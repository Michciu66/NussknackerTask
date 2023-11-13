package com.chudzik.httpadvice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.chudzik.exceptions.NoTicketException;

@ControllerAdvice
public class NoTicketAdvice {
    @ResponseBody
    @ExceptionHandler(NoTicketException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String noTicketHandler(NoTicketException e)
    {
        return e.getMessage();
    }
    
}
