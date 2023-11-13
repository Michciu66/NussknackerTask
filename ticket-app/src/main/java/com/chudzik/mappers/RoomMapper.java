package com.chudzik.mappers;


import com.chudzik.dtos.RoomDto;
import com.chudzik.models.Room;

public class RoomMapper {

    RoomMapper() {

    }

    public static RoomDto convertToDto(Room room) {
        RoomDto out = new RoomDto();
        out.setId(room.getId());
        out.setRoomNumber(room.getRoomNumber());
        out.setSeatList(room.getSeatList().stream()
                .map(SeatMapper::convertToDto)
                .toList());
        return out;
    }

}
