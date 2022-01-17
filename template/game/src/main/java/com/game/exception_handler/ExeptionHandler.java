package com.game.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExeptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> exceptionHandler(IncorrectDataPlayerExeption incorrectDataPlayerExeption){
        return new ResponseEntity<>(incorrectDataPlayerExeption.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> exceptionHandler(PlayerNotFoundExeption playerNotFoundExeption){
        return new ResponseEntity<>(playerNotFoundExeption.getMessage(), HttpStatus.NOT_FOUND);
    }
}
