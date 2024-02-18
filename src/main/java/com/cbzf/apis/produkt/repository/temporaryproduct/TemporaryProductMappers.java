package com.cbzf.apis.produkt.repository.temporaryproduct;

import com.cbzf.apis.produkt.rest.FullProductInputDTO;

import java.util.List;

public class TemporaryProductMappers {

    public List<TemporaryProductEntity> provideEntityFromDto(List<FullProductInputDTO> input) {

        return input.stream().map(dto -> {
            TemporaryProductEntity entity = new TemporaryProductEntity();
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
            entity.setIndeksE(dto.getIndeksE());
            entity.setIndeksV(dto.getIndeksV());
            entity.setIndeksM(dto.getIndeksM());
            entity.setIndeksO(dto.getIndeksO());
            entity.setIndeksF(dto.getIndeksF());
            entity.setIndeksP(dto.getIndeksP());
            entity.setIndeksS(dto.getIndeksS());
            entity.setIndeksT(dto.getIndeksT());
            return entity;
        }).toList();
    }
}
