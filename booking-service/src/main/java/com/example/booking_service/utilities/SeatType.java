package com.example.booking_service.utilities;

import java.util.HashMap;
import java.util.Map;

import java.util.HashMap;
import java.util.Map;

public class SeatType {
    private Map<String, Integer> seatPrices = new HashMap<>();

    // Method to set price dynamically for a seat
    public void setSeatPrice(String seatName, int price) {
        seatPrices.put(seatName, price);
    }

    // Method to get price of a specific seat
    public int getSeatPrice(String seatName) {
        return seatPrices.getOrDefault(seatName, -1);
    }


}
