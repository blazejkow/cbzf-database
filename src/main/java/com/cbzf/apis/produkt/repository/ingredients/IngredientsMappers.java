package com.cbzf.apis.produkt.repository.ingredients;

import com.cbzf.apis.produkt.repository.ingredients.IngredientsEntity;
import com.cbzf.apis.produkt.rest.FullProductInputDTO;

import java.util.List;

public class IngredientsMappers {

    public List<IngredientsEntity> provideEntityFromDto(List<FullProductInputDTO> input) {

        return input.stream().map(dto -> {
            IngredientsEntity entity = new IngredientsEntity();
            entity.setIdProdukt(dto.getIdProdukt());
            entity.setSkladnikIlosc(dto.getSkladnikIlosc());
            entity.setSkladnik1(dto.getSkladnik1());
            entity.setSkladnik2(dto.getSkladnik2());
            entity.setSkladnik3(dto.getSkladnik3());
            entity.setSkladnik4(dto.getSkladnik4());
            entity.setSkladnik5(dto.getSkladnik5());
            entity.setSkladnik6(dto.getSkladnik6());
            entity.setSkladnik7(dto.getSkladnik7());
            entity.setSkladnik8(dto.getSkladnik8());
            entity.setSkladnik9(dto.getSkladnik9());
            entity.setDodatekIlosc(dto.getDodatekIlosc());
            entity.setIdDodatek1(dto.getIdDodatek1());
            entity.setIdDodatek2(dto.getIdDodatek2());
            entity.setIdDodatek3(dto.getIdDodatek3());
            entity.setIdDodatek4(dto.getIdDodatek4());
            entity.setIdDodatek5(dto.getIdDodatek5());
            entity.setIdDodatek6(dto.getIdDodatek6());
            entity.setIdDodatek7(dto.getIdDodatek7());
            entity.setIdDodatek8(dto.getIdDodatek8());
            entity.setAromat(dto.getAromat());
            return entity;
        }).toList();
    }
}
