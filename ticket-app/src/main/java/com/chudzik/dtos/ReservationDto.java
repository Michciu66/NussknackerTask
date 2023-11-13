package com.chudzik.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReservationDto {

    private Long id;
    private String clientName;
    private String clientSurname;
    private Long screeningId;
    private Long roomId;

    private List<Map.Entry<TicketTypeDto, SeatDto>> seatList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientSurname() {
        return clientSurname;
    }

    public void setClientSurname(String clientSurname) {
        this.clientSurname = clientSurname;
    }

    public Long getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(Long screeningId) {
        this.screeningId = screeningId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public List<Map.Entry<TicketTypeDto, SeatDto>> getSeatList() {
        if (seatList == null) {
            seatList = new ArrayList<>();
        }
        return seatList;
    }

    public void setSeatList(List<Map.Entry<TicketTypeDto, SeatDto>> seatList) {
        this.seatList = seatList;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((clientName == null) ? 0 : clientName.hashCode());
        result = prime * result + ((clientSurname == null) ? 0 : clientSurname.hashCode());
        result = prime * result + ((screeningId == null) ? 0 : screeningId.hashCode());
        result = prime * result + ((roomId == null) ? 0 : roomId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof ReservationDto other))
            return false;
        if (!this.id.equals(other.id) || !this.clientName.equals(other.clientName)
                || !this.clientSurname.equals(other.clientSurname) || !this.roomId.equals(other.roomId)
                || !this.screeningId.equals(other.screeningId)) {
            return false;
        }

        if (this.getSeatList().size() != other.getSeatList().size()) {
            return false;
        }
        for (int i = 0; i < this.seatList.size(); i++) {
            if (!this.seatList.get(i).getKey().equals(other.seatList.get(i).getKey())
                    || this.seatList.get(i).getValue().equals(other.seatList.get(i).getValue())) {
                return false;
            }
        }

        return true;
    }
}
