package com.example.booking_service.bookingsDTO;

import java.io.Serializable;
import java.util.List;

import com.example.booking_service.entities.Seat;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO implements Serializable {

    private Long bookingId;

    private Long userId;

    private Long showId;

    private List<Seat> seats;
    private Double finalPrice;

    public BookingDTO(Long bookingId, Long userId, Long showId, Double finalPrice) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.showId = showId;
        this.seats = null; // Explicitly setting seats to null
        this.finalPrice = finalPrice;
    }
}
