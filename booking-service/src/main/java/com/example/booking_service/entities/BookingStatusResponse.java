package com.example.booking_service.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class BookingStatusResponse {

    @Id
    private String bookingid;

    private String status;

    @CreationTimestamp
    private LocalDateTime timestamp;

    public BookingStatusResponse(String bookingid, String status) {
        this.bookingid = bookingid;
        this.status = status;
    }
}
