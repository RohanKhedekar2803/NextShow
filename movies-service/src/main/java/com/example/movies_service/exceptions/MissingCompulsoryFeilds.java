package com.example.movies_service.exceptions;

public class MissingCompulsoryFeilds extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public MissingCompulsoryFeilds(String message) {
        super(message);
    }
}