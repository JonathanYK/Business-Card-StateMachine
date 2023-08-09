package com.wavebl.businesscard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {CardNotFoundException.class})
    public ResponseEntity<RequestException> handleCardNotFoundException(CardNotFoundException e) {
        return createResponse(HttpStatus.BAD_REQUEST, "CARD NOT FOUND ERROR:", e);
    }

    @ExceptionHandler(value = {CardMissingParamsException.class})
    public ResponseEntity<RequestException> handleMissingParamsException(CardMissingParamsException e) {
        return createResponse(HttpStatus.BAD_REQUEST, "MISSING PARAMS ERROR:", e);
    }

    @ExceptionHandler(value = {NextStateNotFoundException.class})
    public ResponseEntity<RequestException> handleStateNotFoundException(NextStateNotFoundException e) {
        return createResponse(HttpStatus.NOT_FOUND, "STATE NOT FOUND ERROR:", e);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<RequestException> handleRuntimeException(RuntimeException e) {
        return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, "RUNTIME ERROR:", e);
    }
    private ResponseEntity<RequestException> createResponse(HttpStatus status, String internalErrorMessage, Exception e) {
        RequestException requestException = new RequestException (
                internalErrorMessage + e.getMessage(),
                status,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(requestException, status);
    }
}
