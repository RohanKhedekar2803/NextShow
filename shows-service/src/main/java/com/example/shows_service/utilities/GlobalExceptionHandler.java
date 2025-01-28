package com.example.shows_service.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.shows_service.DTO.ErrorResponse;
import com.example.shows_service.exceptions.MissingCompulsoryFeilds;


@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(MovieNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleMovieNotFound(MovieNotFoundException ex) {
//    	
//        ErrorResponse errorResponse = ErrorResponse.builder()
//                .message(ex.getMessage())
//                .status(HttpStatus.NOT_FOUND.value())
//                .timestamp(System.currentTimeMillis())
//                .build();
//        
//        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
//    }
    
    @ExceptionHandler(MissingCompulsoryFeilds.class)
    public ResponseEntity<ErrorResponse> handleMissingCompulsoryFeilds(MissingCompulsoryFeilds ex) {
    
    	ErrorResponse errorResponse = ErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(System.currentTimeMillis())
                .build();
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
//    @ExceptionHandler(TheaterNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleMovieNotFound(TheaterNotFoundException ex) {
//    	
//        ErrorResponse errorResponse = ErrorResponse.builder()
//                .message(ex.getMessage())
//                .status(HttpStatus.NOT_FOUND.value())
//                .timestamp(System.currentTimeMillis())
//                .build();
//        
//        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
//    }

}
