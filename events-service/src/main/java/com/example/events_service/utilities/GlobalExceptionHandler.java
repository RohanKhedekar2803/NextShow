package com.example.events_service.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.events_service.exceptions.AuditoriumNotFoundException;
import com.example.events_service.exceptions.EventNotFoundException;
import com.example.events_service.utilities.ErrorResponse;





@ControllerAdvice
public class GlobalExceptionHandler {

    
    @ExceptionHandler(AuditoriumNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMovieNotFound(AuditoriumNotFoundException ex) {
    	
        ErrorResponse errorResponse = ErrorResponse .builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(System.currentTimeMillis())
                .build();
        
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEventNotFound(EventNotFoundException ex) {
    	
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(System.currentTimeMillis())
                .build();
        
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}

