package com.chudzik.exceptions;

public class TicketTypeNotFoundException extends RuntimeException{
    public TicketTypeNotFoundException(Long id)
    {
        super("Ticket Type with id= " + id + " does not exist");
    }

}
