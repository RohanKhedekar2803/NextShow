package com.example.booking_service.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.booking_service.Kafka.KafkaMessagePublisher;

@RestController
@RequestMapping("Bookings")
public class KafkaControllers {

    @Autowired
    private KafkaMessagePublisher kafkaMessagePublisher;

    // @GetMapping("send/{topic}/{msg}") //set topic events-{name of event} if need
    // to process ticket booking
    // public ResponseEntity<?> produce(@PathVariable String topic, @PathVariable
    // String msg) {
    // try {
    // kafkaMessagePublisher.sendToTopic(topic, msg);
    // return ResponseEntity.ok("msg sent");
    // } catch (Exception e) {
    // System.out.println("error is " + e.getMessage());
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    // }
    // }

}
