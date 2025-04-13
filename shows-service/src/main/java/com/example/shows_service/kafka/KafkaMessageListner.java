package com.example.shows_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import com.example.nextshowdto.*;

// @Slf4j
// @Service
// public class KafkaMessageListner {

// // need to configure grioup id oherwiose error will be given
// @KafkaListener(topics = "rohan3", groupId = "rohan-group-01")
// public void consume(BookingDTO msg) {
// try {
// log.info("consumer consumed" + msg);
// } catch (Exception e) {
// System.out.println(e.getMessage());
// }
// }
// }
