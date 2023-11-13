package com.chudzik.exceptions;

public class RoomNotFoundException extends RuntimeException{
    public RoomNotFoundException(Long id)
    {
        super("Room with id: " + id + " does not exist");
    }
}
