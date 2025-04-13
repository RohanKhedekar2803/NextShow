package com.example.booking_service.entities;

import java.util.List;

import com.example.booking_service.utilities.BookingStatus;
import com.example.booking_service.utilities.SeatType;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(exclude = "seats")
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private Long userId;

    private Long showId;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    @JsonManagedReference // to aviod recursive response issue occured due to bidirectional mapping
    private List<Seat> seats;

    private Double finalPrice;

    private BookingStatus bookingStatus;

}

// follow approach of raw sql in this to manage finalized bookings
