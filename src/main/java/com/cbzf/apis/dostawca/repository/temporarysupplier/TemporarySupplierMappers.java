package com.cbzf.apis.dostawca.repository.temporarysupplier;

import com.cbzf.apis.dostawca.rest.SupplierInputDTO;

import java.util.List;

public class TemporarySupplierMappers {

    public List<TemporarySupplierEntity> provideEntityFromDto(List<SupplierInputDTO> input) {

        return input.stream().map(dto -> {
            TemporarySupplierEntity entity = new TemporarySupplierEntity();
            entity.setIdDostawca(dto.getIdDostawca());
            entity.setPi(dto.getPi());
            entity.setIleKodowEan(dto.getIleKodowEan());
            entity.setPar1(dto.getPar1());
            entity.setNazwaDostawca(dto.getNazwaDostawca());
            entity.setAdresDostawca(dto.getAdresDostawca());
            entity.setIdKraj(dto.getIdKraj());
            entity.setNipDostawca(dto.getNipDostawca());
            entity.setRmsdDostawca(dto.getRmsdDostawca());
            entity.setKontaktDostawca(dto.getKontaktDostawca());
            entity.setDlugKodEan1(dto.getDlugKodEan1());
            entity.setKodProdEan1(dto.getKodProdEan1());
            entity.setDlugKodEan2(dto.getDlugKodEan2());
            entity.setKodProdEan2(dto.getKodProdEan2());
            entity.setDlugKodEan3(dto.getDlugKodEan3());
            entity.setKodProdEan3(dto.getKodProdEan3());
            entity.setDlugKodEan4(dto.getDlugKodEan4());
            entity.setKodProdEan4(dto.getKodProdEan4());
            entity.setDataDodania(dto.getDataDodania());
            return entity;
        }).toList();
    }
}
