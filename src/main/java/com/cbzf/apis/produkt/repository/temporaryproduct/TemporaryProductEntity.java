package com.cbzf.apis.produkt.repository.temporaryproduct;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Temporary Product entity reflecting the database structure
 */
@Setter
@Getter
@Entity
@Table(name = "produkt_temporary")
public class TemporaryProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produkt")
    private Integer idProdukt;
    @Column(name = "kod_ean")
    private String kodEan;
    @Column(name = "par1")
    private Integer par1;
    @Column(name = "par2")
    private Boolean par2;
    @Column(name = "id_dostawca")
    private Integer idDostawca;
    @Column(name = "nazwa_produkt")
    private String nazwaProdukt;
    @Column(name = "opis_produkt")
    private String opisProdukt;
    @Column(name = "waga_produkt")
    private String wagaProdukt;
    @Column(name = "opakowanie")
    private String opakowanie;
    @Column(name = "id_kraj")
    private Integer idKraj;
    @Column(name = "liczba_kat")
    private Integer liczbaKat;
    @Column(name = "kategoria")
    private String kategoria;
    @Column(name = "kat1")
    private String kat1;
    @Column(name = "kat2")
    private String kat2;
    @Column(name = "kat3")
    private String kat3;
    @Column(name = "kat4")
    private String kat4;
    @Column(name = "data_dodania")
    private LocalDateTime dataDodania;
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
    @Column(name = "przechowywanie")
    private String przechowywanie;
    @Column(name = "trwalosc")
    private String trwalosc;
    @Column(name = "id_sprzedawca")
    private String idSprzedawca;
    @Column(name = "po_otwarciu")
    private String poOtwarciu;
    @Column(name = "przygotowanie")
    private String przygotowanie;
    @Column(name = "alergeny")
    private String alergeny;
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
    @Column(name = "indeks_e")
    private Integer indeksE;
    @Column(name = "indeks_v")
    private Integer indeksV;
    @Column(name = "indeks_m")
    private Integer indeksM;
    @Column(name = "indeks_o")
    private Integer indeksO;
    @Column(name = "indeks_f")
    private Integer indeksF;
    @Column(name = "indeks_p")
    private Integer indeksP;
    @Column(name = "indeks_s")
    private Integer indeksS;
    @Column(name = "indeks_t")
    private Integer indeksT;
    @Column(name = "approved_by_expert")
    private Boolean approvedByExpert;

    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        dataDodania = LocalDateTime.now();
    }
}
