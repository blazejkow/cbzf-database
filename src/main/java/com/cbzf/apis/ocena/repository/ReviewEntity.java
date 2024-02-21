package com.cbzf.apis.ocena.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Review entity reflecting the database structure
 */
@Setter
@Getter
@Entity
@Table(name = "ocena")
public class ReviewEntity {
    @Id
    @Column(name = "id_produkt")
    private Integer idProdukt;
}
