package com.example.paymentsAndNotifictionService.Config.Kafka;

import com.example.paymentsAndNotifictionService.Entities.Payment;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.nextshowdto.BookingDTO;
import com.example.nextshowdto.PaymnentsServiceBookingObject;
import com.example.paymentsAndNotifictionService.Services.EmailService;
import com.example.paymentsAndNotifictionService.Services.StripePaymentService;
import com.stripe.exception.StripeException;
import com.example.paymentsAndNotifictionService.Cache.PaymentCache;
import com.example.paymentsAndNotifictionService.Cache.StripeInfoStore;
import com.example.paymentsAndNotifictionService.Cache.stripecallbackUserInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Component
public class KafkaMessageConsumer {

    @Autowired
    EmailService emailService;

    @Autowired
    StripePaymentService StripePaymentService;

    @Value("${app.frontend.base-url}")
    private String frontendBaseUrl;

    @KafkaListener(topicPattern = "payment-and-notifications", groupId = "rohan-group-01")
    public void consume(PaymnentsServiceBookingObject obj) {

        String successUrl = frontendBaseUrl + "/paymentsucceded";
        String cancelUrl = frontendBaseUrl + "/failed";

        System.out.println(obj.toString());

        long seatCount = (long) obj.getSeats().size();

        try {
            String key = obj.getUserId() + "-" + obj.getShowId() + "-" + obj.getSeats().get(0).getSeatId(); // also used
                                                                                                            // for
                                                                                                            // stripe to
                                                                                                            // identify
                                                                                                            // booking

            String checkoutString = StripePaymentService.createCheckoutSession(seatCount, obj.getSeatPrice(), "INR",
                    successUrl, cancelUrl, obj, key);

            System.out.println(checkoutString);

            PaymentCache.store(key, checkoutString);
            StripeInfoStore.Store(key, new stripecallbackUserInfo(obj.getUserId(), obj.getShowId(), obj.getSeats()));

            // emailService.sendEmail(obj);
        } catch (StripeException e) {
            System.out.println("Cound't initiate payment Session");
            e.printStackTrace();
        }

        return;
    }

}