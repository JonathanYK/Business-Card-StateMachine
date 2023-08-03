package com.wavebl.businesscard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {CardNotFoundException.class})
    public ResponseEntity<Object> handleCardNotFoundException(CardNotFoundException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        String InternalErrMsg = "CARD NOT FOUND ERROR:";
        // creates RequestException according to CardNotFoundException values
        RequestException requestException = new RequestException (
                InternalErrMsg + e.getMessage(),
                badRequest,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(requestException, badRequest);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {CardMissingParamsException.class})
    public ResponseEntity<Object> handleMissingParamsException(CardMissingParamsException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        String InternalErrMsg = "MISSING PARAMS ERROR:";
        RequestException requestException = new RequestException (
                InternalErrMsg + e.getMessage(),
                badRequest,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(requestException, badRequest);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {NextStateNotFoundException.class})
    public ResponseEntity<Object> handleStateNotFoundException(NextStateNotFoundException e) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        String InternalErrMsg = "STATE NOT FOUND ERROR:";
        RequestException requestException = new RequestException (
                InternalErrMsg + e.getMessage(),
                notFound,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(requestException, notFound);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e) {
        HttpStatus notFound = HttpStatus.INTERNAL_SERVER_ERROR;
        String InternalErrMsg = "RUNTIME ERROR:";
        RequestException requestException = new RequestException (
                InternalErrMsg + e.getMessage(),
                notFound,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(requestException, notFound);
    }

}
