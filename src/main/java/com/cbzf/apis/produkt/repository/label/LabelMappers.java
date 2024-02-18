package com.cbzf.apis.produkt.repository.label;

import com.cbzf.apis.produkt.rest.FullProductInputDTO;

import java.util.List;

public class LabelMappers {

    public List<LabelEntity> provideEntityFromDto(List<FullProductInputDTO> input) {

        return input.stream().map(dto -> {
            LabelEntity entity = new LabelEntity();
            entity.setIdProdukt(dto.getIdProdukt());
            entity.setPrzechowywanie(dto.getPrzechowywanie());
            entity.setTrwalosc(dto.getTrwalosc());
            entity.setIdSprzedawca(dto.getIdSprzedawca());
            entity.setPoOtwarciu(dto.getPoOtwarciu());
            entity.setPrzygotowanie(dto.getPrzygotowanie());
            entity.setAlergeny(dto.getAlergeny());
            return entity;
        }).toList();
    }
}
