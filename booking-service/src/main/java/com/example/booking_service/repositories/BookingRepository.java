package com.example.booking_service.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.booking_service.entities.Booking;
import com.example.booking_service.utilities.BookingStatus;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {

    List<Booking> findByBookingStatus(BookingStatus completed);

}
