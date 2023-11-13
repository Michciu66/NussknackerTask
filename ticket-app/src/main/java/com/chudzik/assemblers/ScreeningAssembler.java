package com.chudzik.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.chudzik.controllers.ScreeningController;
import com.chudzik.dtos.ScreeningDto;


@Component
public class ScreeningAssembler implements RepresentationModelAssembler<ScreeningDto,EntityModel<ScreeningDto>>{ 
    
    @Override
    public EntityModel<ScreeningDto> toModel(ScreeningDto screening)
    {
        return EntityModel.of(screening, 
        linkTo(methodOn(ScreeningController.class).findScreeningById(screening.getId())).withSelfRel(),
        linkTo(methodOn(ScreeningController.class).listScreenings()).withRel("screenings"));
    }
}
