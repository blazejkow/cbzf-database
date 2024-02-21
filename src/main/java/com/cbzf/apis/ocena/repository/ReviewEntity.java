package com.cbzf.apis.ocena.repository;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Review entity reflecting the database structure
 */
@Setter
@Getter
@Entity
@IdClass(ReviewEntity.class)
@Table(name = "ocena")
public class ReviewEntity {
    @Id
    @Column(name = "id_produkt")
    private Integer idProdukt;
    @Id
    @Column(name = "id_parametr")
    private Integer idParametr;
    @Column(name = "nazwa_grupa")
    private String nazwaGrupa;
    @Column(name = "nazwa_par")
    private String nazwaPar;
    @Column(name = "value")
    private Boolean value;
    @Column(name = "data_dodania")
    private LocalDateTime dataDodania;

    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        dataDodania = LocalDateTime.now();
    }
}
