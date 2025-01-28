package com.example.shows_service.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Shows {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate ShowID
    private Long showId;
    
	@Column(nullable = false)
    private Long movieId;
    
    @Column(nullable = false)
    private Long screenId;
    
    @Column(nullable = false)
    private Long theaterId;
    
    @Column(nullable = false)
    private LocalDateTime startTime;
    
    @Column(nullable = false)
    private LocalDateTime endTime;
    
    @Column(nullable = false)
    private Double price;
    
}
