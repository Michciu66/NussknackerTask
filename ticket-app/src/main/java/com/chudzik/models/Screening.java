package com.chudzik.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import com.chudzik.enums.TicketType;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SCREENING_ID",nullable = false)
    private Long id;
    @DateTimeFormat(style = "yyyy-MM-dd HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "SCREENING_TIME",nullable = false,length = 16)
    private LocalDateTime time;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SCREENING_MOVIE")
    private Movie movie;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SCREENING_ROOM")
    private Room room;

    @OneToMany(mappedBy = "screening", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Reservation> reservationList;

    @ElementCollection
    @CollectionTable(name = "SCREENING_SEATLIST")
    private Map<Long, Seat> seatList;

    //Each seat is given a unique identifier generated using the cantorPair() function
    //from its seatNumber and seatRow. Since we cannot assume the rooms seat list is sorted
    //this makes it possible to find neighboring seats without searching through the list every time.

    Screening() {
        reservationList = new ArrayList<>();
        seatList = new HashMap<>();
    }

    public Screening(LocalDateTime time, Movie movie, Room room) {
        this();
        this.time = time;
        this.movie = movie;
        this.room = room;
        for (Seat seat : room.getSeatList()) {
            Seat input = new Seat(seat.getSeatRow(), seat.getSeatNumber());
            seatList.put(input.cantorPair(), input);
        }
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

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    public Map<Long, Seat> getSeatList() {
        return seatList;
    }

    public void setSeatList(Map<Long, Seat> seatList) {
        this.seatList = seatList;
    }

    public void addReservation(Reservation reservation) {
        if (!reservationList.contains(reservation)) {
            this.reservationList.add(reservation);
        }
        for (Map.Entry<Seat, TicketType> seat : reservation.getSeatList().entrySet()) {
            seatList.get(seat.getKey().cantorPair()).setReserved(true);
        }
    }

    public void removeReservation(Reservation reservation) {
        reservationList.removeIf(n -> (n.equals(reservation)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Screening)) {
            return false;
        }
        Screening other = (Screening) o;
        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.movie, this.reservationList, this.room, this.time, this.seatList);
    }

    @Override
    public String toString() {
        return "Screening [id=" + id + ", time=" + time + ", movie=" + movie + ", room=" + room + "]";
    }

}
