package com.cbzf.apis.wartoscodzywcza.repository;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class NutritionPrimaryKey implements Serializable {

    private Integer idProdukt;
    private Integer idNutrient;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NutritionPrimaryKey that = (NutritionPrimaryKey) o;

        if (!Objects.equals(idProdukt, that.idProdukt)) return false;
        return Objects.equals(idNutrient, that.idNutrient);
    }

    @Override
    public int hashCode() {
        int result = idProdukt != null ? idProdukt.hashCode() : 0;
        result = 31 * result + (idNutrient != null ? idNutrient.hashCode() : 0);
        return result;
    }
}
