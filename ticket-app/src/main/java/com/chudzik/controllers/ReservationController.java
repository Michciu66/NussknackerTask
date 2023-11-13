package com.chudzik.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chudzik.assemblers.ReservationCreationAssembler;
import com.chudzik.assemblers.ReservationAssembler;
import com.chudzik.dtos.ReservationCreationDto;
import com.chudzik.dtos.ReservationDto;
import com.chudzik.records.ReservationInfo;
import com.chudzik.services.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationAssembler assembler;
    private final ReservationService service;
    private final ReservationCreationAssembler creationAssembler;

    public ReservationController(ReservationAssembler assembler, ReservationService service,
            ReservationCreationAssembler creationAssembler) {
        this.assembler = assembler;
        this.service = service;
        this.creationAssembler = creationAssembler;
    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    public CollectionModel<EntityModel<ReservationDto>> listReservations() {
        List<EntityModel<ReservationDto>> entityScreenings = service.listReservations().stream()
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(entityScreenings,
                linkTo(methodOn(ReservationController.class).listReservations()).withSelfRel());
    }

    @GetMapping(path =  "/{id}", produces = "application/json;charset=UTF-8")
    public EntityModel<ReservationDto> findReservationById(@PathVariable Long id) {

        return assembler.toModel(service.findReservationById(id));
    }

    @PutMapping(path = "/create", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> createReservation(@RequestBody ReservationInfo info) {

        EntityModel<ReservationCreationDto> out = creationAssembler.toModel(service
                .createReservations(info.clientName(), info.clientSurname(), info.screeningId(), info.tickets()));

        return new ResponseEntity<>(out, HttpStatus.CREATED);

    }
}
