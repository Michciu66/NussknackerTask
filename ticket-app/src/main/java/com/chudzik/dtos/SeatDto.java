package com.chudzik.dtos;

public class SeatDto {

    private int seatRow;
    private int seatNumber;
    private boolean isReserved;

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

    @Override
    public String toString() {
        return "SeatDto [seatRow=" + seatRow + ", seatNumber=" + seatNumber + ", isReserved=" + isReserved + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + seatRow;
        result = prime * result + seatNumber;
        result = prime * result + (isReserved ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof SeatDto other))
            return false;
        return (seatRow != other.seatRow || seatNumber != other.seatNumber || isReserved != other.isReserved);
    }
}
