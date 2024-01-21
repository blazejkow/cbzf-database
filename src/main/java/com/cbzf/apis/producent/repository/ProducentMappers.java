package com.cbzf.apis.producent.repository;

import com.cbzf.apis.producent.rest.ProducentInputDTO;

import java.util.List;

/**
 * Class holding entity to dto and dto to entity mappers for Producent objects
 */
public class ProducentMappers {

    public List<ProducentEntity> provideEntityFromDto(List<ProducentInputDTO> input) {

        return input.stream().map(dto -> {
            ProducentEntity entity = new ProducentEntity();
            entity.setIdProducent(dto.getIdProducent());
            entity.setIleKodowEan(dto.getIleKodowEan());
            entity.setPar1(dto.getPar1());
            entity.setNazwaProducent(dto.getNazwaProducent());
            entity.setAdresProducent(dto.getAdresProducent());
            entity.setIdKraj(dto.getIdKraj());
            entity.setNipProducent(dto.getNipProducent());
            entity.setRmsdProducent(dto.getRmsdProducent());
            entity.setKontaktProducent(dto.getKontaktProducent());
            entity.setDlugKodEan1(dto.getDlugKodEan1());
            entity.setKodProdEan1(dto.getKodProdEan1());
            entity.setDlugKodEan2(dto.getDlugKodEan2());
            entity.setKodProdEan2(dto.getKodProdEan2());
            entity.setDlugKodEan3(dto.getDlugKodEan3());
            entity.setKodProdEan3(dto.getKodProdEan3());
            entity.setDlugKodEan4(dto.getDlugKodEan4());
            entity.setKodProdEan4(dto.getKodProdEan4());
            return entity;
        }).toList();
    }
}
