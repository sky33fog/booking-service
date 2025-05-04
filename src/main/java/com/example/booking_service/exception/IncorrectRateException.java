package com.example.booking_service.exception;

public class IncorrectRateException extends RuntimeException {
    public IncorrectRateException(String message) {
        super(message);
    }
}
