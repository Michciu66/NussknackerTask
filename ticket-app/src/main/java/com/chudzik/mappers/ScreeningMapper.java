package com.chudzik.mappers;


import java.util.stream.Collectors;

import com.chudzik.dtos.ScreeningDto;
import com.chudzik.models.Screening;

public class ScreeningMapper {

    ScreeningMapper() {

    }

    public static ScreeningDto convertToDto(Screening screening) {
        ScreeningDto out = new ScreeningDto();
        out.setId(screening.getId());
        out.setMovie(MovieMapper.convertToDto(screening.getMovie()));
        out.setReservationList(screening.getReservationList().stream()
                .map(ReservationMapper::convertToDto)
                .collect(Collectors.toList()));
        out.setRoomId(screening.getRoom().getId());
        out.setTime(screening.getTime());
        out.setSeatList(screening.getSeatList().values().stream()
                .map(SeatMapper::convertToDto)
                .toList());
        return out;
    }

    public static ScreeningDto convertToLimited(Screening screening)
    {
        ScreeningDto out = new ScreeningDto();

        out.setId(screening.getId());
        out.setMovie(MovieMapper.convertToDto(screening.getMovie()));
        out.setRoomId(screening.getRoom().getId());
        out.setTime(screening.getTime());
        return out;
    }
}
