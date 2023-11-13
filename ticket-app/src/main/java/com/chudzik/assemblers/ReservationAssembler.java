package com.chudzik.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.chudzik.controllers.ReservationController;
import com.chudzik.dtos.ReservationDto;

@Component
public class ReservationAssembler implements RepresentationModelAssembler<ReservationDto,EntityModel<ReservationDto>>{


    @Override
    public EntityModel<ReservationDto> toModel(ReservationDto reservation)
    {
        return EntityModel.of(reservation, 
        linkTo(methodOn(ReservationController.class).findReservationById(reservation.getId())).withSelfRel(),
        linkTo(methodOn(ReservationController.class).listReservations()).withRel("reservations"));
    }
}
