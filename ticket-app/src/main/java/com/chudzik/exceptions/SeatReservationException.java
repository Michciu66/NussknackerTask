package com.chudzik.exceptions;

public class SeatReservationException extends RuntimeException{
    public SeatReservationException(int seatNumber, int seatRow)
    {
        super("Operation would create gap in reserved seats in seat number: " + seatNumber + " in row: " + seatRow);
    }

    public SeatReservationException(int ticketNumber)
    {
        super("Ticket number " + ticketNumber + " doesn't contain a valid seat");
    }
}
