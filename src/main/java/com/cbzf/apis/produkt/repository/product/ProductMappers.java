package com.cbzf.apis.produkt.repository.product;

import com.cbzf.apis.produkt.repository.product.ProductEntity;
import com.cbzf.apis.produkt.rest.FullProductInputDTO;

import java.util.List;

public class ProductMappers {

    public List<ProductEntity> provideEntityFromDto(List<FullProductInputDTO> input) {

        return input.stream().map(dto -> {
            ProductEntity entity = new ProductEntity();
            entity.setIdProdukt(dto.getIdProdukt());
            entity.setKodEan(dto.getKodEan());
            entity.setPar1(dto.getPar1());
            entity.setPar2(dto.getPar2());
            entity.setIdDostawca(dto.getIdDostawca());
            entity.setNazwaProdukt(dto.getNazwaProdukt());
            entity.setOpisProdukt(dto.getOpisProdukt());
            entity.setWagaProdukt(dto.getWagaProdukt());
            entity.setOpakowanie(dto.getOpakowanie());
            entity.setIdKraj(dto.getIdKraj());
            entity.setLiczbaKat(dto.getLiczbaKat());
            entity.setKategoria(dto.getKategoria());
            entity.setKat1(dto.getKat1());
            entity.setKat2(dto.getKat2());
            entity.setKat3(dto.getKat3());
            entity.setKat4(dto.getKat4());
            entity.setDataDodania(dto.getDataDodania());
            return entity;
        }).toList();
    }
}
