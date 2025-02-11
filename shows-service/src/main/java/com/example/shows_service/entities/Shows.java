package com.example.shows_service.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
    private Long eventId;
    
    @Column(nullable = false)
    private Long auditoriumId;
    
    @Column(nullable = false)
    private LocalDateTime startTime;
    
    @Column(nullable = false)
    private LocalDateTime endTime;
    
    @Column(nullable = false)
    @ElementCollection
    private List<Integer> blockprices;
    
}
