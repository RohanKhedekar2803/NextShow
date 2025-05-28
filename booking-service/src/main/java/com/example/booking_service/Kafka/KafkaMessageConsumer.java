package com.example.booking_service.Kafka;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.booking_service.services.BookingService;
import com.example.nextshowdto.BookingDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Component
public class KafkaMessageConsumer {

    @Autowired
    private BookingService bookingService;

    // need to configure grioup id oherwiose error will be given

    @KafkaListener(topicPattern = "events-.*", groupId = "rohan-group-01")
    public void consume(BookingDTO bookingDTO) {

        try {
            System.out.println("consumsed booking at topic [" + "events-*" + "] msg ---> \n"
                    + bookingService.createBooking(bookingDTO.getUserId(), bookingDTO.getShowId(),
                            bookingDTO.getSeats()));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
