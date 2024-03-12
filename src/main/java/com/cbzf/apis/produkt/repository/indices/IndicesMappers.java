package com.cbzf.apis.produkt.repository.indices;

import com.cbzf.apis.produkt.rest.FullProductInputDTO;

import java.util.List;

public class IndicesMappers {

    public List<IndicesEntity> provideEntityFromDto(List<FullProductInputDTO> input) {

        return input.stream().map(dto -> {
            IndicesEntity entity = new IndicesEntity();
            entity.setIdProdukt(dto.getIdProdukt());
            entity.setIndeksE(dto.getIndeksE());
            entity.setIndeksF(dto.getIndeksF());
            entity.setIndeksM(dto.getIndeksM());
            entity.setIndeksO(dto.getIndeksO());
            entity.setIndeksP(dto.getIndeksP());
            entity.setIndeksS(dto.getIndeksS());
            entity.setIndeksT(dto.getIndeksT());
            entity.setIndeksV(dto.getIndeksV());
            return entity;
        }).toList();
    }
}
