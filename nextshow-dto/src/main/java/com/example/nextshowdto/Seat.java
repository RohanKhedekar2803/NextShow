package com.example.nextshowdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

    private String seatId; // showid-blockid-row-col EG- S101-Z08-C12-R12

    private Double SeatPrice;

    private SeatStatus seatStatus;

    private Booking booking;

}
