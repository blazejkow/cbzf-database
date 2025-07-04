package com.cbzf.apis.wartoscodzywcza.repository;

import jakarta.persistence.*;
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
    private Double procentRws;
    @Column(name = "zawartosc_porcja")
    private Double zawartoscPorcja;
    @Column(name = "procent_rws_porcja")
    private Double procentRwsPorcja;
    @Column(name = "indeks")
    private Integer indeks;
    @Column(name = "legenda")
    private String legenda;

    @PrePersist
    @PreUpdate
    private void setDefaultPorcja() {
        if (this.porcja == null) {
            this.porcja = 100.0;
        }
    }
}
