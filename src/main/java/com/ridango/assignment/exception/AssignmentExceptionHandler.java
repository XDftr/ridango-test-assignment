package com.ridango.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AssignmentExceptionHandler {
    @ExceptionHandler(CocktailApiException.class)
    public ResponseEntity<String> handleCocktailApiException(CocktailApiException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(GameSessionException.class)
    public ResponseEntity<String> handleGameSessionException(GameSessionException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
