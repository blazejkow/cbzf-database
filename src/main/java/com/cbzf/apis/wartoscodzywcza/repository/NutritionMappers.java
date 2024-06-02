package com.cbzf.apis.wartoscodzywcza.repository;

import com.cbzf.apis.wartoscodzywcza.rest.NutritionInputDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
            entity.setZawartosc(scalePortion(dto.getZawartosc(), dto.getPorcja()));
            entity.setJednostka(dto.getJednostka());
            entity.setProcentRws(scalePortion(dto.getProcentRws(), dto.getPorcja()));
            entity.setZawartoscPorcja(conditionPortion(dto.getZawartosc(), dto.getPorcja()));
            entity.setProcentRwsPorcja(conditionPortion(dto.getProcentRws(), dto.getPorcja()));
            entity.setIndeks(dto.getIndeks());
            entity.setLegenda(provideLegendValue(dto.getNazwaGrupy(), dto.getNazwa(), dto.getIndeks()));
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

    private Double conditionPortion(Double firstValue, Double secondValue) {
        if (secondValue != null && secondValue != 100) {
            return roundToTwoDecimalPlaces(firstValue);
        }
        return null;
    }

    private Double scalePortion(Double firstValue, Double secondValue) {
        if (firstValue == null) {
            return null;
        }
        if (secondValue != null && secondValue != 100) {
            return roundToTwoDecimalPlaces(firstValue * 100 / secondValue);
        }
        return roundToTwoDecimalPlaces(firstValue);
    }

    private Double roundToTwoDecimalPlaces(Double value) {
        if (value == null) {
            return null;
        }
        BigDecimal bd = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private String provideLegendValue(String nazwaGrupy, String nazwa, Integer indeks) {


        Integer one = 1;
        Integer two = 2;
        if ("wartość energetyczna".equalsIgnoreCase(nazwaGrupy) && one.equals(indeks)) {
            return "Niska lub zmniejszona wartość energetyczna";
        } else if ("tłuszcz".equalsIgnoreCase(nazwaGrupy) && "total".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
            return "Niska zawartość tłuszczu";
        } else if ("tłuszcz".equalsIgnoreCase(nazwaGrupy) && "total".equalsIgnoreCase(nazwa) && two.equals(indeks)) {
            return "Nie zawiera tłuszczu";
        } else if ("tłuszcz".equalsIgnoreCase(nazwaGrupy) && "kwasy nasycone".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
            return "Niska zawartość tłuszczów nasyconych";
        } else if ("tłuszcz".equalsIgnoreCase(nazwaGrupy) && "kwasy nasycone".equalsIgnoreCase(nazwa) && two.equals(indeks)) {
            return "Nie zawiera tłuszczów nasyconych";
        } else if ("tłuszcz".equalsIgnoreCase(nazwaGrupy) && "kwasy jednonienasycone".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
            return "Niska zawartość tłuszczów jednonienasyconych";
        } else if ("tłuszcz".equalsIgnoreCase(nazwaGrupy) && "kwasy wielonienasycone".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
            return "Niska zawartość tłuszczów wielonienasyconych";
        } else if ("tłuszcz".equalsIgnoreCase(nazwaGrupy) && "kwasy wielonienasycone".equalsIgnoreCase(nazwa) && two.equals(indeks)) {
            return "Nie zawiera tłuszczów wielonienasyconych";
        } else if ("węglowodany".equalsIgnoreCase(nazwaGrupy) && "cukry".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
            return "Niska zawartość cukrów";
        } else if ("węglowodany".equalsIgnoreCase(nazwaGrupy) && "cukry".equalsIgnoreCase(nazwa) && two.equals(indeks)) {
            return "Nie zawiera cukrów";
        } else if ("węglowodany".equalsIgnoreCase(nazwaGrupy) && "cukry dodane".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
            return "Bez dodatku cukrów";
        } else if ("błonnik".equalsIgnoreCase(nazwaGrupy)) {
            if (indeks == null) {
                return null;
            }
            switch (indeks) {
                case 1:
                    return "Źródło błonnika";
                case 2:
                case 3:
                    return "Wysoka zawartość błonnika pokarmowego";
            }
        } else if ("białko".equalsIgnoreCase(nazwaGrupy)) {
            if (indeks == null) {
                return null;
            }
            return switch (indeks) {
                case 1 -> "Źródło białka";
                case 2, 3 -> "Wysoka zawartość białka";
                default -> null;
            };
        } else if ("sól".equalsIgnoreCase(nazwaGrupy)) {
            if (indeks == null) {
                return null;
            }
            return switch (indeks) {
                case 1 -> "Niska zawartość soli/sodu";
                case 2 -> "Bardzo niska zawartość soli/sodu";
                case 3 -> "Nie zawiera soli/sodu";
                default -> null;
            };
        } else if ("witaminy".equalsIgnoreCase(nazwaGrupy) || "witaminy inne".equalsIgnoreCase(nazwaGrupy)) {
            return provideVitaminLegend(nazwa, indeks);
        } else if ("minerały".equalsIgnoreCase(nazwaGrupy) && "potas".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
            return "Źródło potasu";
        } else {
            Integer three = 3;
            boolean indicesComparison = two.equals(indeks) || three.equals(indeks);
            if ("minerały".equalsIgnoreCase(nazwaGrupy) && "potas".equalsIgnoreCase(nazwa) && indicesComparison) {
                return "Wysoka zawartość potasu";
            } else if ("minerały".equalsIgnoreCase(nazwaGrupy) && "wapń".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
                return "Źródło wapnia";
            } else if ("minerały".equalsIgnoreCase(nazwaGrupy) && "wapń".equalsIgnoreCase(nazwa) && indicesComparison) {
                return "Wysoka zawartość wapnia";
            } else if ("minerały".equalsIgnoreCase(nazwaGrupy) && "fosfor".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
                return "Źródło fosforu";
            } else if ("minerały".equalsIgnoreCase(nazwaGrupy) && "fosfor".equalsIgnoreCase(nazwa) && indicesComparison) {
                return "Wysoka zawartość fosforu";
            } else if ("minerały".equalsIgnoreCase(nazwaGrupy) && "magnez".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
                return "Źródło magnezu";
            } else if ("minerały".equalsIgnoreCase(nazwaGrupy) && "magnez".equalsIgnoreCase(nazwa) && indicesComparison) {
                return "Wysoka zawartość magnezu";
            } else if ("minerały".equalsIgnoreCase(nazwaGrupy) && "żelazo".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
                return "Źródło żelaza";
            } else if ("minerały".equalsIgnoreCase(nazwaGrupy) && "żelazo".equalsIgnoreCase(nazwa) && indicesComparison) {
                return "Wysoka zawartość żelaza";
            } else if ("minerały".equalsIgnoreCase(nazwaGrupy) && "cynk".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
                return "Źródło cynku";
            } else if ("minerały".equalsIgnoreCase(nazwaGrupy) && "cynk".equalsIgnoreCase(nazwa) && indicesComparison) {
                return "Wysoka zawartość cynku";
            } else if ("minerały".equalsIgnoreCase(nazwaGrupy) && "fluorek".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
                return "Źródło fluorków";
            } else if ("minerały".equalsIgnoreCase(nazwaGrupy) && "fluorek".equalsIgnoreCase(nazwa) && indicesComparison) {
                return "Wysoka zawartość fluorków";
            } else if ("minerały".equalsIgnoreCase(nazwaGrupy) && "mangan".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
                return "Źródło manganu";
            } else if ("minerały".equalsIgnoreCase(nazwaGrupy) && "mangan".equalsIgnoreCase(nazwa) && indicesComparison) {
                return "Wysoka zawartość manganu";
            } else if ("minerały".equalsIgnoreCase(nazwaGrupy) && "miedź".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
                return "Źródło miedzi";
            } else if ("minerały".equalsIgnoreCase(nazwaGrupy) && "miedź".equalsIgnoreCase(nazwa) && indicesComparison) {
                return "Wysoka zawartość miedzi";
            } else if ("minerały".equalsIgnoreCase(nazwaGrupy) && "jod".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
                return "Źródło jodu";
            } else if ("minerały".equalsIgnoreCase(nazwaGrupy) && "jod".equalsIgnoreCase(nazwa) && indicesComparison) {
                return "Wysoka zawartość jodu";
            } else if ("minerały".equalsIgnoreCase(nazwaGrupy) && "selen".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
                return "Źródło selenu";
            } else if ("minerały".equalsIgnoreCase(nazwaGrupy) && "selen".equalsIgnoreCase(nazwa) && indicesComparison) {
                return "Wysoka zawartość selenu";
            } else if ("minerały".equalsIgnoreCase(nazwaGrupy) && "molibden".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
                return "Źródło molibdenu";
            } else if ("minerały".equalsIgnoreCase(nazwaGrupy) && "molibden".equalsIgnoreCase(nazwa) && indicesComparison) {
                return "Wysoka zawartość molibdenu";
            } else if ("minerały".equalsIgnoreCase(nazwaGrupy) && "chrom".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
                return "Źródło chromu";
            } else if ("minerały".equalsIgnoreCase(nazwaGrupy) && "chrom".equalsIgnoreCase(nazwa) && indicesComparison) {
                return "Wysoka zawartość chromu";
            } else if ("omega-3".equalsIgnoreCase(nazwaGrupy) && one.equals(indeks)) {
                return "Źródło kwasów tłuszczowych omega-3";
            } else if ("omega-3".equalsIgnoreCase(nazwaGrupy) && indicesComparison) {
                return "Wysoka zawartość kwasów tłuszczowych omega-3";
            } else if ("antyoksydanty".equalsIgnoreCase(nazwaGrupy) && "polifenole".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
                return "Zawiera polifenole";
            } else if ("antyoksydanty".equalsIgnoreCase(nazwaGrupy) && "flawonoidy".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
                return "Zawiera flawonoidy";
            } else if ("żywe bakterie".equalsIgnoreCase(nazwaGrupy) && "żywe kultury jogurtowe".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
                return "Zawiera żywe kultury jogurtowe";
            } else if ("żywe bakterie".equalsIgnoreCase(nazwaGrupy) && "Bifidobacterium".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
                return "Zawiera bakterie Bifidobacterium";
            } else if ("żywe bakterie".equalsIgnoreCase(nazwaGrupy) && "Lactobacillus".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
                return "Zawiera bakterie Lactobacillus";
            } else if ("oligo/polisacharydy".equalsIgnoreCase(nazwaGrupy) && "laktuloza".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
                return "Zawiera laktulozę";
            } else if ("oligo/polisacharydy".equalsIgnoreCase(nazwaGrupy) && "alfa-cyklodekstryna".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
                return "Zawiera alfa-cyklodekstrynę";
            } else if ("oligo/polisacharydy".equalsIgnoreCase(nazwaGrupy) && "chitozan".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
                return "Zawiera chitozan";
            } else if ("oligo/polisacharydy".equalsIgnoreCase(nazwaGrupy) && "inulina".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
                return "Zawiera inulinę";
            } else if ("glikozydy".equalsIgnoreCase(nazwaGrupy) && "betanina".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
                return "Zawiera betaninę";
            } else if ("kwasy organiczne".equalsIgnoreCase(nazwaGrupy) && "kreatyna".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
                return "Zawiera kreatynę";
            } else if ("enzymy".equalsIgnoreCase(nazwaGrupy) && "laktaza".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
                return "Zawiera laktazę";
            } else if ("hormony".equalsIgnoreCase(nazwaGrupy) && "melatonina".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
                return "Zawiera melatoninę";
            } else if ("sterole/stanole".equalsIgnoreCase(nazwaGrupy) && "fitosterole/stanole/estry".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
                return "Zawiera fitosterole/stanole/estry";
            } else if ("fosfolipidy".equalsIgnoreCase(nazwaGrupy) && "lecytyna".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
                return "Zawiera lecytynę";
            } else if ("aminokwasy egzogenne".equalsIgnoreCase(nazwaGrupy) && one.equals(indeks)) {
                return provideAminokwasyEgzLegend(nazwa);
            } else if ("inne".equalsIgnoreCase(nazwaGrupy) && "węgiel aktywowany".equalsIgnoreCase(nazwa) && one.equals(indeks)) {
                return "Zawiera węgiel aktywowany";
            }
        }
        return null;
    }

    private String provideVitaminLegend(String nazwa, Integer indeks) {
        if (indeks == null) {
            return null;
        }
        String vitType;
        if (nazwa.startsWith("Witamina B")) {
            vitType = nazwa.substring(nazwa.length() - 3);
        } else {
            vitType = nazwa.substring(nazwa.length() - 1);
        }
        return switch (indeks) {
            case 1 -> "Źródło witaminy " + vitType;
            case 2, 3 -> "Wysoka zawartość witaminy " + vitType;
            default -> null;
        };
    }

    private String provideAminokwasyEgzLegend(String nazwa) {
        String prefix = nazwa.substring(0, nazwa.length() - 1);
        if ("tryptofan".equalsIgnoreCase(nazwa)) {
            return "Zawiera "+nazwa;
        }
        return "Zawierą " + prefix + " ę";
    }
}
