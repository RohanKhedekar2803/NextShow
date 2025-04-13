package com.example.events_service.Kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.example.nextshowdto.*;

@Slf4j
@Service
public class KafkaMessageListner {

    // // need to configure grioup id oherwiose error will be given
    // @KafkaListener(topics = "rohan4", groupId = "rohan-group-01")
    // public void consume(BookingDTO msg) {
    // try {
    // log.info("consumer consumed" + msg);
    // } catch (Exception e) {
    // System.out.println(e.getMessage());
    // }
    // }

    // // need to configure grioup id oherwiose error will be given
    // @KafkaListener(topicPattern = "events*", groupId = "rohan-group-01")
    // public void consume(String bookingDTO) {
    // try {
    // System.out.println("consumsed at topic [" + "rohan4" + "] msg ---> \n");

    // } catch (Exception e) {
    // System.out.println(e.getMessage());
    // }
    // }
}
