package com.example.events_service.utilities;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

    private String message;
    private int status;
    private long timestamp;
    
}
