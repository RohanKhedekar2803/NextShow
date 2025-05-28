package com.example.paymentsAndNotifictionService.Kafka;

import com.example.paymentsAndNotifictionService.Entities.Payment;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.nextshowdto.BookingDTO;
import com.example.nextshowdto.PaymnentsServiceBookingObject;
import com.example.paymentsAndNotifictionService.Services.EmailService;
import com.example.paymentsAndNotifictionService.Services.StripePaymentService;
import com.stripe.exception.StripeException;
import com.example.paymentsAndNotifictionService.Cache.PaymentCache;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Component
public class KafkaMessageConsumer {

    @Autowired
    EmailService emailService;

    @Autowired
    StripePaymentService StripePaymentService;

    // need to configure grioup id oherwiose error will be given

    @KafkaListener(topicPattern = "payment-and-notifications", groupId = "rohan-group-01")
    public void consume(PaymnentsServiceBookingObject obj) {
        System.out.println(obj.toString());

        long seatCount = (long) obj.getSeats().size();

        try {
            String checkoutString = StripePaymentService.createCheckoutSession(seatCount, obj.getSeatPrice(), "INR",
                    "http://localhost:5173/paymentsucceded", "http://localhost:5173/failed",obj);

            System.out.println(checkoutString);

            String key = obj.getUserId() + "-" + obj.getShowId() + "-" + obj.getSeats().get(0).getSeatId();
            PaymentCache.store(key, checkoutString);

            emailService.sendEmail(obj);
        } catch (StripeException e) {
            System.out.println("Cound't initiate payment Session");
            e.printStackTrace();
        }

        return;
    }

}