package com.cbzf.apis.produkt.repository.product;

import com.cbzf.apis.produkt.repository.indices.IndicesEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Product entity reflecting the database structure
 */
@Setter
@Getter
@Entity
@Table(name = "produkt")
public class ProductEntity {
    @Id
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

    @OneToOne(mappedBy = "product")
    @JsonManagedReference
    private IndicesEntity indicesEntity;

    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        dataDodania = LocalDateTime.now();
    }
}
