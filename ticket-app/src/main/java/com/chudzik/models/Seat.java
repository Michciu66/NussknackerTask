package com.chudzik.models;

import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class Seat {

    private int seatRow;
    private int seatNumber;
    private boolean isReserved = false;

    public Seat() {

    }

    public Seat(int seatNumber, int seatRow) {
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
    }

    public int getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(int seatRow) {
        this.seatRow = seatRow;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean isReserved) {
        this.isReserved = isReserved;
    }

    public Long cantorPair() // returns a unique natural number for every pair of natural numbers.
    {
        return (long) ((seatNumber + seatRow) * (seatNumber + seatRow + 1) / 2) + seatRow;
    }

    public static Long cantorPair(int seatNumber, int seatRow) // returns a unique natural number for every pair of
                                                               // natural numbers.
    {
        return (long) ((seatNumber + seatRow) * (seatNumber + seatRow + 1) / 2) + seatRow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Seat)) {
            return false;
        }
        Seat other = (Seat) o;
        return this.cantorPair().equals(other.cantorPair());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.seatNumber, this.seatRow);
    }

    @Override
    public String toString() {
        return "Seat [seatRow=" + seatRow + ", seatNumber=" + seatNumber + ", isReserved=" + isReserved
                + "]";
    }

}
