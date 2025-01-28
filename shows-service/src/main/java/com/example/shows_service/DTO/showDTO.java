package com.example.shows_service.DTO;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class showDTO {
	
    private Long showId;
    
    private Long movieId;
    
    private Long screenId;
    
    private Long theaterId;
    
    private LocalDateTime startTime;
    
    private LocalDateTime endTime;
    
    private Double price;
    
}
