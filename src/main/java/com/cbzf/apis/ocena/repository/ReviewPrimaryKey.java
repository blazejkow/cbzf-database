package com.cbzf.apis.ocena.repository;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ReviewPrimaryKey implements Serializable {

    private Integer idProdukt;
    private Integer idParametr;

    public ReviewPrimaryKey() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewPrimaryKey that = (ReviewPrimaryKey) o;
        return Objects.equals(idProdukt, that.idProdukt) &&
                Objects.equals(idParametr, that.idParametr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProdukt, idParametr);
    }
}
