package com.cbzf.apis.produkt.repository;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Ingredients entity reflecting the database structure
 */
@Setter
@Getter
@Entity
@Table(name = "sklad")
public class IngredientsEntity {
    @Id
    @Column(name = "id_produkt")
    private Integer idProdukt;
    @Column(name = "skladnik_ilosc")
    private Integer skladnikIlosc;
    @Column(name = "skladnik1")
    private String skladnik1;
    @Column(name = "skladnik2")
    private String skladnik2;
    @Column(name = "skladnik3")
    private String skladnik3;
    @Column(name = "skladnik4")
    private String skladnik4;
    @Column(name = "skladnik5")
    private String skladnik5;
    @Column(name = "skladnik6")
    private String skladnik6;
    @Column(name = "skladnik7")
    private String skladnik7;
    @Column(name = "skladnik8")
    private String skladnik8;
    @Column(name = "skladnik9")
    private String skladnik9;
    @Column(name = "dodatek_ilosc")
    private Integer dodatekIlosc;
    @Column(name = "id_dodatek1")
    private Integer idDodatek1;
    @Column(name = "id_dodatek2")
    private Integer idDodatek2;
    @Column(name = "id_dodatek3")
    private Integer idDodatek3;
    @Column(name = "id_dodatek4")
    private Integer idDodatek4;
    @Column(name = "id_dodatek5")
    private Integer idDodatek5;
    @Column(name = "id_dodatek6")
    private Integer idDodatek6;
    @Column(name = "id_dodatek7")
    private Integer idDodatek7;
    @Column(name = "id_dodatek8")
    private Integer idDodatek8;
    @Column(name = "aromat")
    private String aromat;
}
