package com.chudzik.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.chudzik.controllers.ReservationController;
import com.chudzik.controllers.ScreeningController;
import com.chudzik.dtos.ReservationCreationDto;

@Component
public class ReservationCreationAssembler implements RepresentationModelAssembler<ReservationCreationDto,EntityModel<ReservationCreationDto>>{

    
    @Override
    public EntityModel<ReservationCreationDto> toModel(ReservationCreationDto reservationCreationDto)
    {
        return EntityModel.of(reservationCreationDto,
        linkTo(methodOn(ReservationController.class).findReservationById(reservationCreationDto.getReservation().getId())).withRel("reservations"),
        linkTo(methodOn(ScreeningController.class).findScreeningById(reservationCreationDto.getReservation().getScreeningId())).withRel("screenings"));
    }
}
