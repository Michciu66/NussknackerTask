package com.chudzik.controllers;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import com.chudzik.assemblers.ScreeningAssembler;
import com.chudzik.dtos.ScreeningDto;
import com.chudzik.services.ScreeningService;


@RestController
@RequestMapping("/screenings")
public class ScreeningController {

    private final ScreeningAssembler assembler;
    private final ScreeningService service;

    public ScreeningController(ScreeningAssembler assembler,
            ScreeningService service) {
        this.assembler = assembler;
        this.service = service;
    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    public CollectionModel<EntityModel<ScreeningDto>> listScreenings() {
        List<EntityModel<ScreeningDto>> entityScreenings = service.listScreenings().stream()
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(entityScreenings,
                linkTo(methodOn(ScreeningController.class).listScreenings()).withSelfRel());
    }

    @GetMapping(path = "/{id}", produces = "application/json;charset=UTF-8")
    public EntityModel<ScreeningDto> findScreeningById(@PathVariable Long id) {

        return assembler.toModel(service.findScreeningById(id));
    }

    @GetMapping(path = "/byDate", produces = "application/json;charset=UTF-8")
    public CollectionModel<EntityModel<ScreeningDto>> listScreeningsBetweenDateTime(
            @RequestParam String startDate) {

        List<EntityModel<ScreeningDto>> screenings = service.listScreeningsBetweenDateTime(startDate).stream()
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(screenings,
                linkTo(methodOn(ScreeningController.class).listScreeningsBetweenDateTime(startDate))
                        .withSelfRel());
    }
}
