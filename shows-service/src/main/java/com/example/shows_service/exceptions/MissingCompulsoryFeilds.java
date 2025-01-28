package com.example.shows_service.exceptions;

public class MissingCompulsoryFeilds extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public MissingCompulsoryFeilds(String message) {
        super(message);
    }
}