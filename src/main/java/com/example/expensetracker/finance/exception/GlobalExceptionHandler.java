package com.example.expensetracker.finance.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public Map<String, Object> handleValidation(MethodArgumentNotValidException e) {
        Map<String, String> details = new HashMap<>();

        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            details.put(error.getField(), error.getDefaultMessage());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("error", "VALIDATION_ERROR");
        response.put("details", details);
        return response;
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            UserNotFoundException.class,
            CategoryNotFoundException.class,
            TransactionNotFoundException.class
    })
    public Map<String, String> handleNotFound(RuntimeException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "NOT_FOUND");
        response.put("message", ex.getMessage());
        return response;
    }
}
