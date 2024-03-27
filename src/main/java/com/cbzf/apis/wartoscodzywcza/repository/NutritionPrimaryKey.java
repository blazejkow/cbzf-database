package com.cbzf.apis.wartoscodzywcza.repository;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

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

        if (idProdukt != null ? !idProdukt.equals(that.idProdukt) : that.idProdukt != null) return false;
        return idNutrient != null ? idNutrient.equals(that.idNutrient) : that.idNutrient == null;
    }

    @Override
    public int hashCode() {
        int result = idProdukt != null ? idProdukt.hashCode() : 0;
        result = 31 * result + (idNutrient != null ? idNutrient.hashCode() : 0);
        return result;
    }
}
