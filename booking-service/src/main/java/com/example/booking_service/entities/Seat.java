package com.example.booking_service.entities;

import com.example.booking_service.utilities.SeatStatus;
import com.example.booking_service.utilities.SeatType;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(exclude = "booking")
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

    @Id
    private String seatId; // showid-blockid-row-col EG- S101-Z08-C12-R12

    private Double SeatPrice;

    private SeatStatus seatStatus;

    @ManyToOne
    @JsonBackReference
    private Booking booking;

    public void setSeatStatus(String seatStatus2) {
        if (seatStatus2 != null) {
            try {
                // Convert string to uppercase and match with enum
                this.seatStatus = SeatStatus.valueOf(seatStatus2.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid seat status: " + seatStatus2);
            }
        }
    }

    public void setSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }

}
