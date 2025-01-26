package com.example.movies_service.exceptions;

public class ScreenNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public ScreenNotFoundException(String message) {
        super(message);
    }
}
