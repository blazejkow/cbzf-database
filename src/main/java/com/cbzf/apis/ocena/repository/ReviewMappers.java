package com.cbzf.apis.ocena.repository;

import com.cbzf.apis.ocena.rest.ReviewInputDTO;

import java.util.List;

/**
 * Class holding entity to dto and dto to entity mappers for Review objects
 */
public class ReviewMappers {

    public List<ReviewEntity> provideEntityFromDto(List<ReviewInputDTO> input) {

        return input.stream().map(dto -> {
            ReviewEntity entity = new ReviewEntity();
            entity.setIdProdukt(dto.getIdProdukt());
            entity.setIdParametr(dto.getIdParametr());
            entity.setNazwaGrupa(dto.getNazwaGrupa());
            entity.setNazwaPar(dto.getNazwaPar());
            entity.setValue(dto.getValue());
            entity.setDataDodania(dto.getDataDodania());
            return entity;
        }).toList();
    }
}
