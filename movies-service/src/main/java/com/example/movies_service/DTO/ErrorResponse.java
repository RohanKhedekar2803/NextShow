package com.example.movies_service.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

    private String message;
    private int status;
    private long timestamp;
    
}
