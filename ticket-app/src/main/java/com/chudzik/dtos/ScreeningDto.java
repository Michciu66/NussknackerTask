package com.chudzik.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ScreeningDto {



    private Long id;
    private LocalDateTime time;
    private MovieDto movie;
    private Long roomId;

    private List<ReservationDto> reservationList;
    private List<SeatDto> seatList;

    public ScreeningDto() {
        reservationList = new ArrayList<>();
        seatList = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public MovieDto getMovie() {
        return movie;
    }

    public void setMovie(MovieDto movie) {
        this.movie = movie;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public List<ReservationDto> getReservationList() {
        if (reservationList == null) {
            reservationList = new ArrayList<>();
        }
        return reservationList;
    }

    public void setReservationList(List<ReservationDto> reservationList) {
        this.reservationList = reservationList;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((time == null) ? 0 : time.hashCode());
        result = prime * result + ((movie == null) ? 0 : movie.hashCode());
        result = prime * result + ((roomId == null) ? 0 : roomId.hashCode());
        result = prime * result + ((reservationList == null) ? 0 : reservationList.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScreeningDto other)) {
            return false;
        }
        if (!this.id.equals(other.id) || !this.time.equals(other.time)
                || !this.movie.equals(other.movie) || !this.roomId.equals(other.roomId)) {
            return false;
        }
        if (this.getReservationList().size() != other.getReservationList().size()) {
            return false;
        }
        return this.reservationList.equals(other.reservationList);
    }


}
