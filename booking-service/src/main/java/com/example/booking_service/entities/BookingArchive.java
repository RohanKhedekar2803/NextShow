package com.example.booking_service.entities;

import java.time.LocalDateTime;
import java.util.List;
import com.example.booking_service.utilities.BookingStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingArchive {

    @Id
    private Long bookingId;

    private Long userId;

    private Long showId;

    @OneToMany(mappedBy = "bookingArchive", cascade = CascadeType.ALL)
    private List<SeatArchive> seats;

    private Double finalPrice;

    private BookingStatus bookingStatus;

    private LocalDateTime archivedAt; // Timestamp for tracking
}
