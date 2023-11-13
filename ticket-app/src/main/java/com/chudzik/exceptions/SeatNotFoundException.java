package com.chudzik.exceptions;

public class SeatNotFoundException extends RuntimeException{
    public SeatNotFoundException(Integer seatNumber, Integer seatRow, Long roomId)
    {
        super("Seat with seatNumber " + seatNumber + " in row: " + seatRow + " does not exist in room with id " + roomId);
    }



}
