package com.example.booking_service.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.booking_service.entities.Seat;
import com.example.booking_service.utilities.SeatStatus;

public interface SeatRepository extends CrudRepository<Seat, String> {
    List<Seat> findBySeatIdInAndSeatStatus(List<String> seatIds, SeatStatus seatStatus);

    List<Seat> findBySeatIdIn(List<String> seatIds);
}
