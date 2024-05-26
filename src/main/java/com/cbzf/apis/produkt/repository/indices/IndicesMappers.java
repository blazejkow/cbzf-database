package com.cbzf.apis.produkt.repository.indices;

import com.cbzf.apis.produkt.rest.FullProductInputDTO;
import com.cbzf.apis.wartoscodzywcza.rest.NutritionInputDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    public IndicesEntity calculateIndices(List<NutritionInputDTO> dtos) {
        IndicesEntity entity = new IndicesEntity();

        if (!dtos.isEmpty()) {
            entity.setIdProdukt(dtos.get(0).getIdProdukt());
        }

        entity.setIndeksE(findIndexForGroup(dtos, "Wartość Energetyczna"));
        entity.setIndeksV(sumIndicesForGroup(dtos, "Witaminy"));
        entity.setIndeksM(sumIndicesForGroup(dtos, "Minerały"));
        entity.setIndeksO(sumIndicesForGroup(dtos, "Omega-3"));
        entity.setIndeksF(findIndexForGroup(dtos, "Błonnik"));
        entity.setIndeksP(findIndexForGroup(dtos, "Białko"));
        // Calculate indeks_s and indeks_t based on the rules
        entity.setIndeksT(calculateIndeksT(entity));
        entity.setIndeksS(calculateIndeksS(entity, dtos));

        return entity;
    }

    private Integer findIndexForGroup(List<NutritionInputDTO> dtos, String group) {
        return dtos.stream()
                .filter(dto -> group.equalsIgnoreCase(dto.getNazwaGrupy()))
                .findFirst()
                .map(NutritionInputDTO::getIndeks)
                .orElse(0);
    }

    private Integer sumIndicesForGroup(List<NutritionInputDTO> dtos, String group) {
        return dtos.stream()
                .filter(dto -> group.equalsIgnoreCase(dto.getNazwaGrupy()) && !"Total".equalsIgnoreCase(dto.getNazwa()))
                .map(NutritionInputDTO::getIndeks)
                .filter(Objects::nonNull)
                .reduce(0, Integer::sum);
    }

    private Integer findIndexForGroupAndName(List<NutritionInputDTO> dtos, String group, String name) {
        return dtos.stream()
                .filter(dto -> group.equalsIgnoreCase(dto.getNazwaGrupy()) && name.equalsIgnoreCase(dto.getNazwa()))
                .findFirst()
                .map(NutritionInputDTO::getIndeks)
                .orElse(0);
    }

    private Integer calculateIndeksS(IndicesEntity entity, List<NutritionInputDTO> dtos) {
        Integer indeksSol = findIndexForGroup(dtos, "Sól");
        Integer indeksWartoscEnergetyczna = findIndexForGroup(dtos, "Wartość energetyczna");
        Integer indeksCukry = findIndexForGroupAndName(dtos, "Węglowodany", "Cukry");
        Integer indeksCukryDodane = findIndexForGroupAndName(dtos, "Węglowodany", "Cukry dodane");
        Integer indeksKwasyNasycone = findIndexForGroupAndName(dtos, "Tłuszcz", "Kwasy Nasycone");
        Integer indeksKwasyJednonienasycone = findIndexForGroupAndName(dtos, "Tłuszcz", "Kwasy Jednonienasycone");
        Integer indeksKwasyWielonienasycone = findIndexForGroupAndName(dtos, "Tłuszcz", "Kwasy Wielonienasycone");
        Integer indeksTluszczTotal = findIndexForGroupAndName(dtos, "Tłuszcz", "Total");

        return sumNullableIntegers(
                entity.getIndeksT(), indeksWartoscEnergetyczna, indeksKwasyNasycone, indeksCukryDodane,
                indeksSol, indeksCukry, indeksKwasyJednonienasycone,
                indeksKwasyWielonienasycone, indeksTluszczTotal);
    }

    private Integer calculateIndeksT(IndicesEntity entity) {
        return sumNullableIntegers(
                entity.getIndeksV(), entity.getIndeksM(), entity.getIndeksO(),
                entity.getIndeksF(), entity.getIndeksP());
    }

    private Integer sumNullableIntegers(Integer... values) {
        return Arrays.stream(values)
                .filter(Objects::nonNull)
                .reduce(0, Integer::sum);
    }
}
