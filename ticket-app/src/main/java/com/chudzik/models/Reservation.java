package com.chudzik.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.chudzik.enums.TicketType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RESERVATION_ID")
    private Long id;
    @Column(name = "RESERVATION_NAME", nullable = false, length = 51)
    private String clientName;
    @Column(name = "RESERVATION_SURNAME", nullable = false, length = 51)
    private String clientSurname;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RESERVATION_SCREENING")
    private Screening screening;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RESERVATION_ROOM")
    private Room room;
    @ElementCollection
    @CollectionTable(name = "RESERVATION_TICKETLIST")
    private Map<Seat, TicketType> ticketList;

    Reservation() {
        ticketList = new HashMap<>();
    }

    public Reservation(String clientName, String clientSurname, Screening screening, Room room) {
        this();
        this.clientName = clientName;
        this.clientSurname = clientSurname;
        this.screening = screening;
        this.room = room;
    }

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

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Map<Seat, TicketType> getSeatList() {
        return ticketList;
    }

    public void setSeatList(Map<Seat, TicketType> ticketList) {
        this.ticketList = ticketList;
    }

    public void addSeat(Integer seatNumber, Integer seatRow, TicketType ticketType) {
        Seat seat = new Seat(seatNumber, seatRow);
        if (!ticketList.containsKey(seat)) {
            ticketList.put(seat, ticketType);
        }
    }

    public void addSeat(Integer seatNumber, Integer seatRow, TicketType ticketType, boolean isReserved) {
        Seat seat = new Seat(seatNumber, seatRow);
        seat.setReserved(isReserved);
        if (!ticketList.containsKey(seat)) {
            ticketList.put(seat, ticketType);
        }
    }

    public void removeSeat(Seat seat) {
        ticketList.remove(seat);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reservation)) {
            return false;
        }
        Reservation other = (Reservation) o;
        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.clientName, this.clientSurname, this.room, this.screening,
                this.ticketList);
    }

    @Override
    public String toString() {
        return "Reservation [id=" + id + ", clientName=" + clientName + ", clientSurname="
                + clientSurname + ", screening=" + screening + ", room=" + room + "]";
    }

}
