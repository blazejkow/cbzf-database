package com.cbzf.apis.ocena.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReviewInputDTO {
    private Integer idProdukt;
    private Integer idParametr;
    private String nazwaGrupa;
    private String nazwaPar;
    private Boolean value;
    private LocalDateTime dataDodania;
}
