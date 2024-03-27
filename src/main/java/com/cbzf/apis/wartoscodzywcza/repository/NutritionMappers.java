package com.cbzf.apis.wartoscodzywcza.repository;

import com.cbzf.apis.wartoscodzywcza.rest.NutritionInputDTO;

import java.util.List;

public class NutritionMappers {

    public List<NutritionEntity> provideEntityFromDto(List<NutritionInputDTO> input) {

        return input.stream().map(dto -> {
            NutritionEntity entity = new NutritionEntity();
            NutritionPrimaryKey key = new NutritionPrimaryKey();
            key.setIdProdukt(dto.getIdProdukt());
            key.setIdNutrient(dto.getIdNutrient());
            entity.setNutritionPrimaryKey(key);
            entity.setPar1Nutrition(dto.getPar1Nutrition());
            entity.setPar2Nutrition(dto.getPar2Nutrition());
            entity.setPorcja(dto.getPorcja());
            entity.setNazwaGrupy(dto.getNazwaGrupy());
            entity.setNazwa(dto.getNazwa());
            entity.setZawartosc(dto.getZawartosc());
            entity.setJednostka(dto.getJednostka());
            entity.setProcentRws(dto.getProcentRws());
            entity.setZawartoscPorcja(dto.getZawartosc()*dto.getPorcja()/100);
            entity.setProcentRwsPorcja(dto.getProcentRws()*dto.getPorcja()/100);
            entity.setIndeks(dto.getIndeks());
            return entity;
        }).toList();
    }

    public List<TemporaryNutritionEntity> provideTemporaryEntityFromDto(List<NutritionInputDTO> input) {

        return input.stream().map(dto -> {
            TemporaryNutritionEntity entity = new TemporaryNutritionEntity();
            NutritionPrimaryKey key = new NutritionPrimaryKey();
            key.setIdProdukt(dto.getIdProdukt());
            key.setIdNutrient(dto.getIdNutrient());
            entity.setNutritionPrimaryKey(key);
            entity.setPar1Nutrition(dto.getPar1Nutrition());
            entity.setPar2Nutrition(dto.getPar2Nutrition());
            entity.setPorcja(dto.getPorcja());
            entity.setNazwaGrupy(dto.getNazwaGrupy());
            entity.setNazwa(dto.getNazwa());
            entity.setZawartosc(dto.getZawartosc());
            entity.setJednostka(dto.getJednostka());
            entity.setProcentRws(dto.getProcentRws());
            entity.setIndeks(dto.getIndeks());
            return entity;
        }).toList();
    }
}
