package com.cbzf.apis.wartoscodzywcza.repository;

import com.cbzf.apis.wartoscodzywcza.rest.NutritionInputDTO;

import java.util.List;

public class NutritionMappers {

    public List<NutritionEntity> provideEntityFromDto(List<NutritionInputDTO> input) {

        return input.stream().map(dto -> {
            NutritionEntity entity = new NutritionEntity();
            entity.setIdProdukt(dto.getIdProdukt());
            entity.setPar1Nutrition(dto.getPar1Nutrition());
            entity.setPar2Nutrition(dto.getPar2Nutrition());
            entity.setPorcja(dto.getPorcja());
            entity.setIdNutrient(dto.getIdNutrient());
            entity.setNazwaGrupy(dto.getNazwaGrupy());
            entity.setNazwa(dto.getNazwa());
            entity.setZawartosc(dto.getZawartosc());
            entity.setJednostka(dto.getJednostka());
            entity.setProcentRws(dto.getProcentRws());
            entity.setZawartoscPorcja(dto.getZawartoscPorcja());
            entity.setProcentRwsPorcja(dto.getProcentRwsPorcja());
            entity.setIndeks(dto.getIndeks());
            return entity;
        }).toList();
    }

    public List<TemporaryNutritionEntity> provideTemporaryEntityFromDto(List<NutritionInputDTO> input) {

        return input.stream().map(dto -> {
            TemporaryNutritionEntity entity = new TemporaryNutritionEntity();
            entity.setIdProdukt(dto.getIdProdukt());
            entity.setPar1Nutrition(dto.getPar1Nutrition());
            entity.setPar2Nutrition(dto.getPar2Nutrition());
            entity.setPorcja(dto.getPorcja());
            entity.setIdNutrient(dto.getIdNutrient());
            entity.setNazwaGrupy(dto.getNazwaGrupy());
            entity.setNazwa(dto.getNazwa());
            entity.setZawartosc(dto.getZawartosc());
            entity.setJednostka(dto.getJednostka());
            entity.setProcentRws(dto.getProcentRws());
            entity.setZawartoscPorcja(dto.getZawartoscPorcja());
            entity.setProcentRwsPorcja(dto.getProcentRwsPorcja());
            entity.setIndeks(dto.getIndeks());
            return entity;
        }).toList();
    }
}
