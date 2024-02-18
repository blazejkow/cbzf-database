package com.cbzf.apis.produkt.repository.nutrition;

import com.cbzf.apis.produkt.rest.FullProductInputDTO;

import java.util.List;

public class NutritionMappers {

    public List<NutritionEntity> provideEntityFromDto(List<FullProductInputDTO> input) {

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
}
