package com.cbzf.apis.dostawca.repository.supplier;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Supplier entity reflecting the database structure
 */
@Setter
@Getter
@Entity
@Table(name = "dostawca")
public class SupplierEntity {
    @Id
    @Column(name = "id_dostawca")
    private Integer idDostawca;
    @Column(name = "pi")
    private String pi;
    @Column(name = "ile_kodow_ean")
    private Integer ileKodowEan;
    @Column(name = "par1")
    private Integer par1;
    @Column(name = "nazwa_dostawca")
    private String nazwaDostawca;
    @Column(name = "adres_dostawca")
    private String adresDostawca;
    @Column(name = "id_kraj")
    private Integer idKraj;
    @Column(name = "nip_dostawca")
    private String nipDostawca;
    @Column(name = "rmsd_dostawca")
    private Integer rmsdDostawca;
    @Column(name = "kontakt_dostawca")
    private String kontaktDostawca;
    @Column(name = "kod_prod_ean1")
    private String kodProdEan1;
    @Column(name = "kod_prod_ean2")
    private String kodProdEan2;
    @Column(name = "kod_prod_ean3")
    private String kodProdEan3;
    @Column(name = "kod_prod_ean4")
    private String kodProdEan4;
    @Column(name = "kod_prod_ean5")
    private String kodProdEan5;
    @Column(name = "kod_prod_ean6")
    private String kodProdEan6;
    @Column(name = "kod_prod_ean7")
    private String kodProdEan7;
    @Column(name = "kod_prod_ean8")
    private String kodProdEan8;
    @Column(name = "data_dodania")
    private LocalDateTime dataDodania;

    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        dataDodania = LocalDateTime.now();
    }
}
