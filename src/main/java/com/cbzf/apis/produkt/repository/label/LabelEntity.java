package com.cbzf.apis.produkt.repository.label;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;

/**
 * Label entity reflecting the database structure
 */
@Setter
@Getter
@Entity
@Table(name = "etykieta")
public class LabelEntity {
    @Id
    @Column(name = "id_produkt")
    private Integer idProdukt;
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
    @JdbcTypeCode(Types.VARBINARY)
    @Column(name = "obraz")
    private byte[] obraz;
}
