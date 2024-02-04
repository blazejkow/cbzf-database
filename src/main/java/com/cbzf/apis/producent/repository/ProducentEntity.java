package com.cbzf.apis.producent.repository;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Producent entity reflecting the database structure
 */
@Setter
@Getter
@Entity
@Table(name = "producent")
public class ProducentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producent")
    private Integer idProducent;
    @Column(name = "ile_kodow_ean")
    private Integer ileKodowEan;
    @Column(name = "par1")
    private Integer par1;
    @Column(name = "nazwa_producent")
    private String nazwaProducent;
    @Column(name = "adres_producent")
    private String adresProducent;
    @Column(name = "id_kraj")
    private Integer idKraj;
    @Column(name = "nip_producent")
    private String nipProducent;
    @Column(name = "rmsd_producent")
    private Integer rmsdProducent;
    @Column(name = "kontakt_producent")
    private String kontaktProducent;
    @Column(name = "dlug_kod_ean1")
    private Integer dlugKodEan1;
    @Column(name = "kod_prod_ean1")
    private String kodProdEan1;
    @Column(name = "dlug_kod_ean2")
    private Integer dlugKodEan2;
    @Column(name = "kod_prod_ean2")
    private String kodProdEan2;
    @Column(name = "dlug_kod_ean3")
    private Integer dlugKodEan3;
    @Column(name = "kod_prod_ean3")
    private String kodProdEan3;
    @Column(name = "dlug_kod_ean4")
    private Integer dlugKodEan4;
    @Column(name = "kod_prod_ean4")
    private String kodProdEan4;
}
