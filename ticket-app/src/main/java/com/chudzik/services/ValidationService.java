package com.chudzik.services;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.chudzik.exceptions.InvalidScreeningException;
import com.chudzik.exceptions.SeatReservationException;
import com.chudzik.models.Screening;
import com.chudzik.models.Seat;

@Service
public class ValidationService {

    private ValidationService() {

    }

    public static boolean validateName(String name) {
        if(name == null || name.length()> 50)
        {
            return false;
        }
        Pattern namePattern = Pattern.compile("[A-Z][a-z]{2,}");
        Matcher nameMatcher = namePattern.matcher(name);
        return nameMatcher.find();
    }

    public static boolean validateSurname(String surname) {
        if(surname == null || surname.length()> 50)
        {
            return false;
        }
        Pattern surnamePattern = Pattern.compile("^[A-Z][a-z]{2,}(-[A-Z][a-z]{2,})?$");
        Matcher surnameMatcher = surnamePattern.matcher(surname);
        return surnameMatcher.find();
    }

    public static void checkForGaps(Map<Long, Seat> reservedSeatList, Map<Long, Seat> seatList) {
        for (Map.Entry<Long, Seat> reservedSeat : reservedSeatList.entrySet()) {
            // check if reserving these seats wont create unreserved gaps with already
            // reserved seats
            int seatNumber = reservedSeat.getValue().getSeatNumber();
            int seatRow = reservedSeat.getValue().getSeatRow();
            Seat seatFarLeft = seatList.get(Seat.cantorPair(seatNumber - 2, seatRow));
            Seat seatFarRight = seatList.get(Seat.cantorPair(seatNumber + 2, seatRow));
            Seat seatLeft = seatList.get(Seat.cantorPair(seatNumber - 1, seatRow));
            Seat seatRight = seatList.get(Seat.cantorPair(seatNumber + 1, seatRow));

            if (seatLeft != null && !seatLeft.isReserved()
                    && (seatFarLeft != null && seatFarLeft.isReserved())) {
                throw new SeatReservationException(seatNumber - 1, seatRow);
            }

            if (seatRight != null && !seatRight.isReserved()
                    && (seatFarRight != null && seatFarRight.isReserved())) {
                throw new SeatReservationException(seatNumber + 1, seatRow);
            }

            // check if reserving these seats wont create gaps with each other
            if (reservedSeatList.get(Seat.cantorPair(seatNumber - 2, seatRow)) != null
                    && !seatList.get(Seat.cantorPair(seatNumber - 1, seatRow)).isReserved()) {

                throw new SeatReservationException(seatNumber - 1, seatRow);
            }

            if (reservedSeatList.get(Seat.cantorPair(seatNumber + 2, seatRow)) != null
                    && !seatList.get(Seat.cantorPair(seatNumber + 1, seatRow)).isReserved()) {

                throw new SeatReservationException(seatNumber + 1, seatRow);
            }
        }
    }

    public static void validateScreening(Screening screening)
    {
        if(screening.getTime().minusMinutes(15).isBefore(LocalDateTime.now()))
        {
            throw new InvalidScreeningException(screening.getId());
        }
        
    }
}
