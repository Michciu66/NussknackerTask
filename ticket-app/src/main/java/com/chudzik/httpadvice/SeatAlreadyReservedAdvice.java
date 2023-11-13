package com.chudzik.httpadvice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.chudzik.exceptions.SeatAlreadyReservedException;

@ControllerAdvice
public class SeatAlreadyReservedAdvice {
    @ResponseBody
    @ExceptionHandler(SeatAlreadyReservedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String seatALreadyReservedHandler(SeatAlreadyReservedException e) {
        return e.getMessage();
    }
}
