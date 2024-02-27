package com.cbzf.apis.produkt.repository.nutrition;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Nutrition entity reflecting the database structure
 */
@Setter
@Getter
@Entity
@Table(name = "wartosc_odzywcza")
public class NutritionEntity {
    @Id
    @Column(name = "id_produkt")
    private Integer idProdukt;
    @Column(name = "par1_nutrition")
    private String par1Nutrition;
    @Column(name = "par2_nutrition")
    private String par2Nutrition;
    @Column(name = "porcja")
    private Double porcja;
    @Column(name = "id_nutrient")
    private String idNutrient;
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