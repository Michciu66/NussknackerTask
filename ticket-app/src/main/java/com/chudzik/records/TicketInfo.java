package com.chudzik.records;

import com.chudzik.enums.TicketType;

public record TicketInfo(
    Integer seatNumber,
    Integer seatRow,
    TicketType ticketType
) {
    
}
