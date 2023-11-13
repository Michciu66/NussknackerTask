package com.chudzik.exceptions;

public class InvalidNameException extends RuntimeException{
    public InvalidNameException(String name)
    {
        super(name + " isn't a valid name");
    }
}
