package com.example.booking_service.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.booking_service.entities.BookingStatusResponse;

@Repository
public interface BookingStatusRepository extends JpaRepository<BookingStatusResponse, String> {
    @SuppressWarnings("unchecked")
    BookingStatusResponse save(BookingStatusResponse bookingStatusResponse);
}