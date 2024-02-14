package com.cbzf.apis.dostawca.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SupplierInputDTO {
        private Integer idDostawca;
        private String pi;
        private Integer ileKodowEan;
        private Integer par1;
        private String nazwaDostawca;
        private String adresDostawca;
        private Integer idKraj;
        private String nipDostawca;
        private Integer rmsdDostawca;
        private String kontaktDostawca;
        private Integer dlugKodEan1;
        private String kodProdEan1;
        private Integer dlugKodEan2;
        private String kodProdEan2;
        private Integer dlugKodEan3;
        private String kodProdEan3;
        private Integer dlugKodEan4;
        private String kodProdEan4;
        private LocalDateTime dataDodania;
}
