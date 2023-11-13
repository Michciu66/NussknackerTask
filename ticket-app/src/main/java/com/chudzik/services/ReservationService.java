package com.chudzik.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.chudzik.dtos.ReservationCreationDto;
import com.chudzik.dtos.ReservationDto;
import com.chudzik.exceptions.InvalidNameException;
import com.chudzik.exceptions.NoTicketException;
import com.chudzik.exceptions.ReservationNotFoundException;
import com.chudzik.exceptions.ScreeningNotFoundException;
import com.chudzik.exceptions.SeatAlreadyReservedException;
import com.chudzik.exceptions.SeatNotFoundException;
import com.chudzik.exceptions.SeatReservationException;
import com.chudzik.mappers.ReservationMapper;
import com.chudzik.models.Reservation;
import com.chudzik.models.Screening;
import com.chudzik.models.Seat;
import com.chudzik.records.TicketInfo;
import com.chudzik.repositories.ReservationRepository;
import com.chudzik.repositories.ScreeningRepository;

import jakarta.transaction.Transactional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepo;
    private final ScreeningRepository screeningRepo;

    public ReservationService(ReservationRepository reservationRepo, ScreeningRepository screeningRepo) {
        this.reservationRepo = reservationRepo;
        this.screeningRepo = screeningRepo;
    }

    public List<ReservationDto> listReservations() {
        return reservationRepo.findAll().stream()
                .map(ReservationMapper::convertToDto)
                .toList();
    }

    public ReservationDto findReservationById(Long id) {
        Reservation reservation = reservationRepo.findById(id).orElseThrow(() -> new ReservationNotFoundException(id));
        return ReservationMapper.convertToDto(reservation);
    }

    @Transactional
    public ReservationCreationDto createReservations(String clientName, String clientSurname,
            Long screeningId,
            List<TicketInfo> ticketList) {

        if(screeningId == null)
        {
            throw new ScreeningNotFoundException();
        }

        BigDecimal cost = new BigDecimal(0).setScale(2);
        Screening screening = screeningRepo.findById(screeningId)
                .orElseThrow(() -> new ScreeningNotFoundException(screeningId));
        ValidationService.validateScreening(screening);
        
        if (!(ValidationService.validateName(clientName) && ValidationService.validateSurname(clientSurname))) {
            throw new InvalidNameException(clientName + " " + clientSurname);
        }
        Map<Long, Seat> seatList = screening.getSeatList();
        Map<Long, Seat> reservedSeatList = new HashMap<>();
        List<TicketInfo> ticketSeatList = new ArrayList<>();

        if (ticketList == null || ticketList.isEmpty()) {
            throw new NoTicketException();
        }

        int i=1;
        for (TicketInfo ticket : ticketList) {
            if(ticket.seatNumber() == null || ticket.seatRow() == null)
            {
                throw new SeatReservationException(i);
            }
            Seat screeningSeat = seatList.get(Seat.cantorPair(ticket.seatNumber(), ticket.seatRow()));

            // check if seat exists and if it's not already reserved
            if (screeningSeat == null) {
                throw new SeatNotFoundException(ticket.seatNumber(), ticket.seatRow(),
                        screening.getRoom().getId());
            } else if (screeningSeat.isReserved()) {
                throw new SeatAlreadyReservedException(screeningSeat.getSeatNumber(), screeningSeat.getSeatRow());
            }

            cost = cost.add(ticket.ticketType().getCost());
            ticketSeatList.add(ticket);
            reservedSeatList.put(screeningSeat.cantorPair(), screeningSeat);
            screeningSeat.setReserved(true);
            i++;
        }

        ValidationService.checkForGaps(reservedSeatList, seatList);

        Reservation reservation = new Reservation(clientName, clientSurname, screening, screening.getRoom());
        // Create Reservation, add the seats, make the seats reserved.

        for (TicketInfo ticket : ticketSeatList) {
            reservation.addSeat(ticket.seatNumber(), ticket.seatRow(), ticket.ticketType(),true);
        }

        reservationRepo.save(reservation);

        return new ReservationCreationDto(ReservationMapper.convertToDto(reservation),
                screening.getTime().plusMinutes(30), cost);
    }

}
