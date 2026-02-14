package com.example.paymentsAndNotifictionService.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.nextshowdto.PaymnentsServiceBookingObject;
import com.example.paymentsAndNotifictionService.Feign.UserServiceFeignClient;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserServiceFeignClient userServiceFeignClient;

    @Value("${spring.mail.username}")
    private String NextshowMail; // Injected AFTER object creation

    public Boolean sendEmail(PaymnentsServiceBookingObject obj) {
        String email = retrive_email_from_userservice(obj.getUserId());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(NextshowMail);
        message.setTo(email);
        message.setSubject("Booking Confirmation via Nextshow");
        message.setText("Thanks for booking via next show \n Your booking show is " + obj.getShowId()
                + "\nYour ticket IDs: " + obj.getSeats().stream()
                        .map(seat -> seat.getSeatId()) // Extract seatId from each object
                        .collect(Collectors.joining(", ")));

        mailSender.send(message);
        System.out.println("Mail Sent Successfully!");

        return true;
    }

    public Boolean sendEmail(List<com.example.nextshowdto.Seat> seats, Long getShowId, Long userId,
            boolean paymentCompleted) {

        String email = retrive_email_from_userservice(userId);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(NextshowMail);
        message.setTo(email);

        if (paymentCompleted) {
            message.setSubject("Booking Confirmation via Nextshow");
            message.setText("Thanks for booking via nextshow \n Your booking show is " + getShowId
                    + "\nYour ticket IDs: " + seats.stream()
                            .map(seat -> seat.getSeatId()) // Extract seatId from each object
                            .collect(Collectors.joining(", ")));

        } else {
            message.setSubject("Booking Session Expired for Nextshow");
            message.setText("Your booking via nextshow for \n show " + getShowId
                    + "is failed due to payment not received please try again"
                    + "\nYour ticket IDs: " + seats.stream()
                            .map(seat -> seat.getSeatId()) // Extract seatId from each object
                            .collect(Collectors.joining(", ")));

        }

        mailSender.send(message);
        System.out.println("Mail Sent Successfully!");
        return true;
    }

    String retrive_email_from_userservice(Long userid) {
        return userServiceFeignClient.getUserById(userid);
    }
}
