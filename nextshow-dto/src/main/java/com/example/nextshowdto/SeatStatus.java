package com.example.nextshowdto;

public enum SeatStatus {
    AVAILABLE, // The seat is free to be booked
    RESERVED, // Temporarily reserved (e.g., during booking flow)
    BOOKED // Fully booked (after payment confirmation)
}
