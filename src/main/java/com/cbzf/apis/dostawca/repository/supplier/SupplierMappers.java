package com.cbzf.apis.dostawca.repository.supplier;

import com.cbzf.apis.dostawca.rest.SupplierInputDTO;

import java.util.List;

/**
 * Class holding entity to dto and dto to entity mappers for Supplier objects
 */
public class SupplierMappers {

    public List<SupplierEntity> provideEntityFromDto(List<SupplierInputDTO> input) {

        return input.stream().map(dto -> {
            SupplierEntity entity = new SupplierEntity();
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
            entity.setKodProdEan1(dto.getKodProdEan1());
            entity.setKodProdEan2(dto.getKodProdEan2());
            entity.setKodProdEan3(dto.getKodProdEan3());
            entity.setKodProdEan4(dto.getKodProdEan4());
            entity.setKodProdEan5(dto.getKodProdEan5());
            entity.setKodProdEan6(dto.getKodProdEan6());
            entity.setKodProdEan7(dto.getKodProdEan7());
            entity.setKodProdEan8(dto.getKodProdEan8());
            entity.setDataDodania(dto.getDataDodania());
            return entity;
        }).toList();
    }
}
