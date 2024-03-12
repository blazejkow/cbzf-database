package com.cbzf.apis.wartoscodzywcza.repository;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "wartosc_odzywcza_temporary")
public class TemporaryNutritionEntity {
    @EmbeddedId
    private NutritionPrimaryKey nutritionPrimaryKey;
    @Column(name = "par1_nutrition")
    private String par1Nutrition;
    @Column(name = "par2_nutrition")
    private String par2Nutrition;
    @Column(name = "porcja")
    private Double porcja;
    @Column(name = "nazwa_grupy")
    private String nazwaGrupy;
    @Column(name = "nazwa")
    private String nazwa;
    @Column(name = "zawartosc")
    private Double zawartosc;
    @Column(name = "jednostka")
    private String jednostka;
    @Column(name = "procent_rws")
    private Integer procentRws;
    @Column(name = "zawartosc_porcja")
    private String zawartoscPorcja;
    @Column(name = "procent_rws_porcja")
    private Integer procentRwsPorcja;
    @Column(name = "indeks")
    private Integer indeks;
}
