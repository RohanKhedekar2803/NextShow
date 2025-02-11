package com.example.events_service.exceptions;

public class AuditoriumNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

    public AuditoriumNotFoundException(String message) {
        super(message);
    }
}
