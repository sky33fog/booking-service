package com.example.booking_service.exception;

public class ErrorBookingException extends RuntimeException {
    public ErrorBookingException(String message) {
        super(message);
    }
}
