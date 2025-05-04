package com.example.booking_service.web.controller;

import com.example.booking_service.exception.*;
import com.example.booking_service.web.model.ErrorResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.DateTimeException;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> notValid(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errorMessages = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        String errorMessage = String.join("; ", errorMessages);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errorMessage));
    }

    @ExceptionHandler(IncorrectPeriodException.class)
    public ResponseEntity<ErrorResponse> negativePeriod(IncorrectPeriodException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(ErrorBookingException.class)
    public ResponseEntity<ErrorResponse> errorBooking(ErrorBookingException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<ErrorResponse> incorrectDateTime(DateTimeParseException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Correct date format is: YYYY-MM-DD"));
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> userAlreadyExist(UserAlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(IncorrectRateException.class)
    public ResponseEntity<ErrorResponse> userAlreadyExist(IncorrectRateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getLocalizedMessage()));
    }

}
