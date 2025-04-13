package com.example.booking_service.Kafka;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.example.nextshowdto.PaymnentsServiceBookingObject;
import com.example.nextshowdto.*;

@Service
public class KafkaMessagePublisher {

    @Autowired
    private KafkaTemplate<String, BookingDTO> template;

    @Autowired
    private KafkaTemplate<String, List<com.example.nextshowdto.Seat>> Seattemplate;

    @Autowired
    private KafkaTemplate<String, com.example.nextshowdto.PaymnentsServiceBookingObject> Paymenttemplate;

    private final ConcurrentHashMap<String, CompletableFuture<String>> responseMap = new ConcurrentHashMap<>();

    // public void sendToTopic(String topic, String msg) {

    // BookingDTO bookingDto = new BookingDTO(1L, 101L, 501L, null, 299.99);
    // CompletableFuture<SendResult<String, BookingDTO>> send = template.send(topic,
    // bookingDto);

    // send.whenComplete((res, ex) -> { // asyncronous way
    // if (ex == null) {
    // System.out.println("msg sent is [" + msg + "] to topic [" + topic + "] with
    // offset "
    // + res.getRecordMetadata().offset());
    // } else {
    // System.out.println("msg not sent error is " + ex.getMessage());
    // }
    // });
    // }

    public void AddRequestInQueue(String topic, BookingDTO data) {

        CompletableFuture<SendResult<String, BookingDTO>> send = template.send(topic, data);

        send.whenComplete((res, ex) -> { // asyncronous way
            if (ex == null) {
                System.out.println("msg sent is [" + data + "] to topic [" + topic + "] with offset "
                        + res.getRecordMetadata().offset());
            } else {
                System.out.println("msg not sent error is " + ex.getMessage());
            }
        });
    }

    public void AddPaymnentsServiceBooking(String topic, com.example.nextshowdto.PaymnentsServiceBookingObject obj) {

        CompletableFuture<SendResult<String, com.example.nextshowdto.PaymnentsServiceBookingObject>> send = Paymenttemplate
                .send(topic, obj);

        send.whenComplete((res, ex) -> { // asyncronous way
            if (ex == null) {
                System.out.println("msg sent is [" + obj + "] to topic [" + topic + "] with offset "
                        + res.getRecordMetadata().offset());
            } else {
                System.out.println("msg not sent error is " + ex.getMessage());
            }
        });
    }

}
