package com.chudzik.exceptions;

public class ScreeningNotFoundException extends RuntimeException{
    public ScreeningNotFoundException(Long id)
    {
        super("Screening with id: " + id + " does not exist");
    }

    public ScreeningNotFoundException()
    {
        super("No Screening id found in request");
    }
}
