package com.chudzik.exceptions;

public class ReservationNotFoundException extends RuntimeException{
    public ReservationNotFoundException(Long id)
    {
        super("Reservation with id: " + id + " does not exist");
    }
}
