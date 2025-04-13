package com.example.shows_service.Feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "events-service")
public interface EvenstsServiceClient {

    @GetMapping("eventsAPI/auditoriums/getseats/{auditoriumId}")
    public ResponseEntity<List<List<Integer>>> get_seats_arrangement_by_auditorium_id(@PathVariable Long auditoriumId);

}
