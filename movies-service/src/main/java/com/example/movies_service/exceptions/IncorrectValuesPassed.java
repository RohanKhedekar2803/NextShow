package com.example.movies_service.exceptions;

public class IncorrectValuesPassed extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public IncorrectValuesPassed(String message) {
        super(message);
    }
}