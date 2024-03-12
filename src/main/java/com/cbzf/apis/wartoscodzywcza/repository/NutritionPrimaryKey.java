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
}
