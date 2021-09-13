package com.igor.ipLocator;

import com.igor.ipLocator.exception.GeolocationRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> dataRequestInvalid(ConstraintViolationException ex, WebRequest request){
        return ResponseEntity.badRequest().body("The IP address is not valid");
    }

    @ExceptionHandler(GeolocationRequestException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> dataRequestInvalid(GeolocationRequestException ex, WebRequest request){
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }
}
