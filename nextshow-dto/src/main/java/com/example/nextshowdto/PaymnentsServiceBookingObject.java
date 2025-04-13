package com.example.nextshowdto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymnentsServiceBookingObject {
    Long userId;
    Long showId;
    List<com.example.nextshowdto.Seat> seats;
    Double seatPrice;

    // PaymnentsServiceBookingObject() {
    // }

    // public PaymnentsServiceBookingObject(Long userId, Long showId,
    // List<com.example.nextshowdto.Seat> seats) {
    // this.userId = userId;
    // this.showId = showId;
    // this.seats = seats;
    // }
}
