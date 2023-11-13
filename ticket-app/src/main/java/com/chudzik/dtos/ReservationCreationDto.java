package com.chudzik.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReservationCreationDto {
    private ReservationDto reservation;
    private LocalDateTime time;
    private BigDecimal cost;

    public ReservationCreationDto(ReservationDto reservation, LocalDateTime time, BigDecimal cost) {
        this.reservation = reservation;
        this.time = time;
        this.cost = cost;
    }

    public ReservationDto getReservation() {
        return reservation;
    }

    public void setReservation(ReservationDto reservation) {
        this.reservation = reservation;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

}
