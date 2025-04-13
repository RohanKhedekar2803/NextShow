package com.example.shows_service.kafka;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.example.nextshowdto.BookingDTO;

@Service
public class KafkaMessagePublisher {

    @Autowired
    private KafkaTemplate<String, BookingDTO> template;

    public void sendToTopic(String topic, String msg) {

        BookingDTO bookingDto = new BookingDTO(1L, 101L, 501L, null, 299.99);
        CompletableFuture<SendResult<String, BookingDTO>> send = template.send(topic, bookingDto);

        send.whenComplete((res, ex) -> { // asyncronous way
            if (ex == null) {
                System.out.println("msg sent is [" + msg + "] to topic [" + topic + "] with offset "
                        + res.getRecordMetadata().offset());
            } else {
                System.out.println("msg not sent error is " + ex.getMessage());
            }
        });
    }

}