package com.cbzf.apis.produkt.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FullProductInputDTO {
    private Integer idProdukt;
    private String kodEan;
    private Integer par1;
    private Boolean par2;
    private Integer idDostawca;
    private String nazwaProdukt;
    private String opisProdukt;
    private String wagaProdukt;
    private String opakowanie;
    private Integer idKraj;
    private Integer liczbaKat;
    private String kategoria;
    private String kat1;
    private String kat2;
    private String kat3;
    private String kat4;
    private LocalDateTime dataDodania;
    private Integer skladnikIlosc;
    private String skladnik1;
    private String skladnik2;
    private String skladnik3;
    private String skladnik4;
    private String skladnik5;
    private String skladnik6;
    private String skladnik7;
    private String skladnik8;
    private String skladnik9;
    private Integer dodatekIlosc;
    private Integer idDodatek1;
    private Integer idDodatek2;
    private Integer idDodatek3;
    private Integer idDodatek4;
    private Integer idDodatek5;
    private Integer idDodatek6;
    private Integer idDodatek7;
    private Integer idDodatek8;
    private String aromat;
    private String przechowywanie;
    private String trwalosc;
    private String idSprzedawca;
    private String poOtwarciu;
    private String przygotowanie;
    private String alergeny;
    private String par1Nutrition;
    private String par2Nutrition;
    private Double porcja;
    private String idNutrient;
    private String nazwaGrupy;
    private String nazwa;
    private Double zawartosc;
    private String jednostka;
    private Integer procentRws;
    private String zawartoscPorcja;
    private Integer procentRwsPorcja;
    private Integer indeks;
}