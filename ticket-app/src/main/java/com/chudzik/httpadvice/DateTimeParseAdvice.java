package com.chudzik.httpadvice;

import java.time.format.DateTimeParseException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DateTimeParseAdvice {
    @ResponseBody
    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String dateTimeParseHandler(DateTimeParseException e)
    {
        return e.getMessage() + " - expected input format is yyyy-MM-dd HH:mm";
    }
}
