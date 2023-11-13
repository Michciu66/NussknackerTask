package com.chudzik.mappers;



import com.chudzik.dtos.ReservationDto;
import com.chudzik.models.Reservation;

public class ReservationMapper {

    ReservationMapper() {
    }

    public static ReservationDto convertToDto(Reservation reservation) {
        ReservationDto out = new ReservationDto();
        out.setId(reservation.getId());
        out.setRoomId(reservation.getRoom().getId());
        out.setClientName(reservation.getClientName());
        out.setClientSurname(reservation.getClientSurname());
        out.setScreeningId(reservation.getScreening().getId());
        out.setSeatList(reservation.getSeatList().entrySet().stream()
                .map(TicketTypeMapper::convertToDto)
                .toList());
        return out;

    }
}
