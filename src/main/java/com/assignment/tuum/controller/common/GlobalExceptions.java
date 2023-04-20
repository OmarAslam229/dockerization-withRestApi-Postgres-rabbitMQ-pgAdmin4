package com.assignment.tuum.controller.common;

import com.assignment.tuum.exceptions.IllegalOperations;
import com.assignment.tuum.exceptions.UserNotFoundException;
import com.assignment.tuum.model.enums.Currency;
import com.assignment.tuum.model.enums.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, List<String>>> handleCurrencyValidationErrors(HttpMessageNotReadableException ex) {

        String incomingError= ex.getMessage();
        String error = "";

        // error for floating balance

        if(incomingError.contains("country"))
            error = "Invalid Country ";
        if (incomingError.contains("Currency"))
            error += "Invalid Currency : Allowed Currencies are -> " + EnumSet.allOf(Currency.class);
        if (incomingError.contains("Direction"))
            error += "Invalid Direction : Allowed Directions are -> " + EnumSet.allOf(Direction.class);

        return new ResponseEntity(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalOperations.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, List<String>>> handleIllegalOperationsErrors(IllegalOperations ex) {

        String error = ex.getMessage();
        return new ResponseEntity(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, List<String>>> handleNotFoundException(UserNotFoundException ex) {
        // List<String> errors = Collections.singletonList(ex.getMessage());
        //  return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.NOT_FOUND);
        String error = ex.getMessage();
        return new ResponseEntity(error, new HttpHeaders(), HttpStatus.NOT_FOUND);

    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}
