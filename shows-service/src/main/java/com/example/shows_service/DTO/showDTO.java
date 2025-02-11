package com.example.shows_service.DTO;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class showDTO {
	
    private Long showId;
    
	
    private Long eventId;
    
    
    private Long auditoriumId;
    
    
    private LocalDateTime startTime;
    
    
    private LocalDateTime endTime;
    
    
    @ElementCollection
    private List<Integer> blockprices;
    
}
