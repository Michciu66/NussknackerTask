package com.chudzik.httpadvice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.chudzik.exceptions.TicketTypeNotFoundException;

@ControllerAdvice
public class TicketTypeNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(TicketTypeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String ticketTypeNotFoundHandler(TicketTypeNotFoundException e) {
        return e.getMessage();
    }
}
