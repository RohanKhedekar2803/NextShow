package com.example.paymentsAndNotifictionService.Services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

    public Boolean sendEmail(PaymnentsServiceBookingObject obj) {
        String email = retrive_email_from_userservice(obj.getUserId());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("khedekarrohan35@gmail.com"); // same as your spring.mail.username
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

    String retrive_email_from_userservice(Long userid) {
        return userServiceFeignClient.getUserById(userid);
    }
}
