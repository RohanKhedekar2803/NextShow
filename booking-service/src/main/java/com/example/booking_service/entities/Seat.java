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

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
	
	@Id
	private String seatId; //showid-blockid-row-col EG- S101-Z08-C12-R12
	
    private Double SeatPrice;
    
    private SeatStatus seatStatus;
    
    @ManyToOne
    @JoinColumn(name = "booking") 	
    @JsonBackReference
    private Booking booking;

}
