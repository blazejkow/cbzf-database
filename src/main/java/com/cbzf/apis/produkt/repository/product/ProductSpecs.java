package com.cbzf.apis.produkt.repository.product;

import com.cbzf.apis.produkt.repository.indices.IndicesEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecs {

    public static Specification<ProductEntity> hasIdDostawca(Integer idDostawca) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("idDostawca"), idDostawca);
    }

    public static Specification<ProductEntity> hasIdProdukt(Integer idProdukt) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("idProdukt"), idProdukt);
    }

    public static Specification<ProductEntity> hasNazwaProdukt(String nazwaProdukt) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("nazwaProdukt"), nazwaProdukt);
    }

    public static Specification<ProductEntity> hasIdKraj(Integer idKraj) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("idKraj"), idKraj);
    }

    public static Specification<ProductEntity> hasIndeksT(Integer indeksT) {
        return (root, query, criteriaBuilder) -> {
            if (indeksT == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true)); // always true if indeksT is null
            }

            // Join ProductEntity with IndicesEntity
            Join<ProductEntity, IndicesEntity> indicesJoin = root.join("indicesEntity", JoinType.INNER); // assuming the attribute name in ProductEntity for IndicesEntity is "indicesEntity"

            // Filter on indeksT
            return criteriaBuilder.equal(indicesJoin.get("indeksT"), indeksT);
        };
    }
}
