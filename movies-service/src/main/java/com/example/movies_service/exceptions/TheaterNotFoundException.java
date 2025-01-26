package com.example.movies_service.exceptions;

public class TheaterNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public TheaterNotFoundException(String message) {
        super(message);
    }
}
