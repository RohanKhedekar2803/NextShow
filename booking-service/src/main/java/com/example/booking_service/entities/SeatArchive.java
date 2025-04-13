package com.example.booking_service.entities;

import org.hibernate.annotations.Table;

import com.example.booking_service.utilities.SeatStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatArchive {

    @Id
    private String seatId;

    private Double seatPrice;

    private SeatStatus seatStatus;

    @ManyToOne
    private BookingArchive bookingArchive;
}
