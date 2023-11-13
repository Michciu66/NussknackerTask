package com.chudzik.httpadvice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.chudzik.exceptions.ScreeningNotFoundException;

@ControllerAdvice
public class ScreeningNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(ScreeningNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String screeningNotFoundHandler(ScreeningNotFoundException e) {
        return e.getMessage();
    }
}
