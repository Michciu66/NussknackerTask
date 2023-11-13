package com.chudzik.httpadvice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.chudzik.exceptions.InvalidScreeningException;

@ControllerAdvice
public class InvalidScreeningAdvice {
    @ResponseBody
    @ExceptionHandler(InvalidScreeningException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String screeningNotFoundHandler(InvalidScreeningException e) {
        return e.getMessage();
    }
}
