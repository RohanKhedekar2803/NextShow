package com.example.booking_service.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.booking_service.entities.Booking;
import com.example.booking_service.entities.BookingArchive;
import com.example.booking_service.entities.Seat;
import com.example.booking_service.entities.SeatArchive;
import com.example.booking_service.repositories.BookingArchiveRepository;
import com.example.booking_service.repositories.BookingRepository;
import com.example.booking_service.repositories.SeatArchiveRepository;
import com.example.booking_service.utilities.BookingStatus;

import jakarta.transaction.Transactional;

@Service
public class BookingCleanupService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingArchiveRepository bookingArchiveRepository;

    @Autowired
    private SeatArchiveRepository seatArchiveRepository;

    @Transactional
    @Scheduled(cron = "0 0 2 * * ?") // Runs every day at 2 AM
    public void archiveCompletedBookings() {
        List<Booking> completedBookings = bookingRepository.findByBookingStatus(BookingStatus.COMPLETED);

        for (Booking booking : completedBookings) {
            BookingArchive archiveBooking = BookingArchive.builder()
                    .bookingId(booking.getBookingId())
                    .userId(booking.getUserId())
                    .showId(booking.getShowId())
                    .finalPrice(booking.getFinalPrice())
                    .bookingStatus(booking.getBookingStatus())
                    .archivedAt(LocalDateTime.now())
                    .build();

            List<SeatArchive> archivedSeats = new ArrayList<>();
            for (Seat seat : booking.getSeats()) {
                SeatArchive archivedSeat = SeatArchive.builder()
                        .seatId(seat.getSeatId())
                        .seatPrice(seat.getSeatPrice())
                        .seatStatus(seat.getSeatStatus())
                        .bookingArchive(archiveBooking)
                        .build();
                archivedSeats.add(archivedSeat);
            }

            archiveBooking.setSeats(archivedSeats);

            // Save to archive table
            bookingArchiveRepository.save(archiveBooking);
            seatArchiveRepository.saveAll(archivedSeats);

            // Delete from active tables
            bookingRepository.delete(booking);
        }
        System.out.println("✅ Archived " + completedBookings.size() + " bookings.");
    }
}
