package com.example.nextshowdto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {

    private Long bookingId;

    private Long userId;

    private Long showId;

    private List<Seat> seats;

    private Double finalPrice;
}