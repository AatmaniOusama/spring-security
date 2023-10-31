package com.ous.bio.ws.exceptions;

import com.ous.bio.ws.responses.OtherExceptionErrorMessage;
import com.ous.bio.ws.responses.UserExceptionErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(value = UserException.class)
    public ResponseEntity<Object> HandleUserException(UserException ex, WebRequest request) {

        UserExceptionErrorMessage errorMessage = new UserExceptionErrorMessage(new Date(), ex.getMessage());

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

    }
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> HandleUserException(Exception ex, WebRequest request) {

        OtherExceptionErrorMessage exceptionErrorMessage = new OtherExceptionErrorMessage(new Date(),
                ex.getMessage());

        return new ResponseEntity<>(exceptionErrorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> HandleUserException(MethodArgumentNotValidException ex, WebRequest request) {

        Map<String, String> errors = new HashMap<>();

       ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
       });

        return new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}