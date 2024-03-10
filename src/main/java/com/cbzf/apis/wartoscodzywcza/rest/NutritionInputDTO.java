package com.cbzf.apis.wartoscodzywcza.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NutritionInputDTO {
    private Integer idProdukt;
    private String par1Nutrition;
    private String par2Nutrition;
    private Double porcja;
    private Integer idNutrient;
    private String nazwaGrupy;
    private String nazwa;
    private Double zawartosc;
    private String jednostka;
    private Integer procentRws;
    private String zawartoscPorcja;
    private Integer procentRwsPorcja;
    private Integer indeks;
}
