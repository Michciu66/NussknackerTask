package com.chudzik.exceptions;

public class NoTicketException extends RuntimeException{
    public NoTicketException()
    {
        super("No valid tickets selected");
    }

}
