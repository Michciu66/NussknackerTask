package com.chudzik.exceptions;

public class InvalidScreeningException extends RuntimeException{
    public InvalidScreeningException(Long id)
    {
        super("Screening with id: " + id + " is not a valid screening for this action");
    }
}
