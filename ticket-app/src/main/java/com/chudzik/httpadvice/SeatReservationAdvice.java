package com.chudzik.httpadvice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.chudzik.exceptions.SeatReservationException;

@ControllerAdvice
public class SeatReservationAdvice {
    @ResponseBody
    @ExceptionHandler(SeatReservationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String seatReservationHandler(SeatReservationException e) {
        return e.getMessage();
    }
}