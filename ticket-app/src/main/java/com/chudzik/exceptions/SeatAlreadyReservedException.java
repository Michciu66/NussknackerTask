package com.chudzik.exceptions;

public class SeatAlreadyReservedException extends RuntimeException{
    public SeatAlreadyReservedException(int seatNumber, int seatRow)
    {
        super("Seat number: " + seatNumber + " in row: " + seatRow + " is already reserved.");
    }


}
