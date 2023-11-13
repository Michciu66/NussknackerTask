package com.chudzik.mappers;

import java.util.AbstractMap;
import java.util.Map.Entry;

import com.chudzik.dtos.SeatDto;
import com.chudzik.dtos.TicketTypeDto;
import com.chudzik.enums.TicketType;
import com.chudzik.models.Seat;

public class TicketTypeMapper {
    TicketTypeMapper()
    {

    }

    public static Entry<TicketTypeDto,SeatDto> convertToDto(Entry<Seat,TicketType> entry)
    {
        return new AbstractMap.SimpleEntry<>(
            convertToDto(entry.getValue()),
            SeatMapper.convertToDto(entry.getKey())
        );
    }
    
    public static TicketTypeDto convertToDto(TicketType ticket)
    {
        return switch(ticket){
            case ADULT -> TicketTypeDto.ADULT;
            case STUDENT -> TicketTypeDto.STUDENT;
            case CHILD -> TicketTypeDto.CHILD;
        };
    }
}
