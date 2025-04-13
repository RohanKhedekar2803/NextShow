package com.example.booking_service.Feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "shows-service")
public interface EventsClient {

    @PostMapping("/shows/getvalidityandprice")
    public ResponseEntity<Long> check_seats_validity_and_get_price(List<com.example.nextshowdto.Seat> seats);
}