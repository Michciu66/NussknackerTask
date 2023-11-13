package com.chudzik.mappers;


import java.util.AbstractMap;
import java.util.Map.Entry;

import com.chudzik.dtos.SeatDto;
import com.chudzik.models.Seat;

public class SeatMapper {

    SeatMapper() {

    }

    public static SeatDto convertToDto(Seat seat) {
        SeatDto out = new SeatDto();
        out.setSeatNumber(seat.getSeatNumber());
        out.setSeatRow(seat.getSeatRow());
        out.setReserved(seat.isReserved());
        return out;
    }


    public static Entry<Long,SeatDto> convertToDto(Entry<Long,Seat> entry)
    {
        return new AbstractMap.SimpleEntry<>(
            entry.getKey(),
            convertToDto(entry.getValue())
        );
    }
}
