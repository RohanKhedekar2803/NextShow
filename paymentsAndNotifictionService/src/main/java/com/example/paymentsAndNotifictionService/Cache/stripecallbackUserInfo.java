package com.example.paymentsAndNotifictionService.Cache;

import java.util.List;

import com.example.nextshowdto.Seat;

public class stripecallbackUserInfo {

    private Long userId;
    private Long showId;
    private List<Seat> seats;

    public stripecallbackUserInfo(Long userId, Long showId, List<Seat> seats) {
        this.userId = userId;
        this.showId = showId;
        this.seats = seats;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getShowId() {
        return showId;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}
