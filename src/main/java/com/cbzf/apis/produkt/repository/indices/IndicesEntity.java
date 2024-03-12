package com.cbzf.apis.produkt.repository.indices;

import com.cbzf.apis.produkt.repository.product.ProductEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Indices entity reflecting the database structure
 */
@Setter
@Getter
@Entity
@Table(name = "indeksy")
public class IndicesEntity {
    @Id
    @Column(name = "id_produkt")
    private Integer idProdukt;
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

    @OneToOne
    @JoinColumn(name = "id_produkt", referencedColumnName = "id_produkt")
    @JsonBackReference
    private ProductEntity product;
}
