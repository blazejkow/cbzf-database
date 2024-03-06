package com.cbzf.apis.produkt.repository.product;

import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public static Specification<ProductEntity> hasIdDostawca(Integer idDostawca) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("idDostawca"), idDostawca);
    }

    public static Specification<ProductEntity> hasNazwaProdukt(String nazwaProdukt) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("nazwaProdukt"), nazwaProdukt);
    }

    public static Specification<ProductEntity> hasIdKraj(Integer idKraj) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("idKraj"), idKraj);
    }
}
