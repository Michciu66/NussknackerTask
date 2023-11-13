package com.chudzik.httpadvice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.chudzik.exceptions.InvalidNameException;

@ControllerAdvice
public class InvalidNameAdvice {
    @ResponseBody
    @ExceptionHandler(InvalidNameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String invalidNameHandler(InvalidNameException e) {
        return e.getMessage();
    }
}
