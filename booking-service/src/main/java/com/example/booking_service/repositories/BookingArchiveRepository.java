package com.example.booking_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.booking_service.entities.BookingArchive;

@Repository
public interface BookingArchiveRepository extends JpaRepository<BookingArchive, Long> {
}
