package com.chudzik.dtos;

import java.util.ArrayList;
import java.util.List;

public class RoomDto {

    private Long id;
    private int roomNumber;
    private List<SeatDto> seatList;

    public RoomDto() {
        seatList = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public List<SeatDto> getSeatList() {
        if (seatList == null) {
            seatList = new ArrayList<>();
        }
        return seatList;
    }

    public void setSeatList(List<SeatDto> seatList) {
        this.seatList = seatList;
    }
}
