package com.cbzf.apis.produkt.repository.product;

import com.cbzf.apis.produkt.repository.indices.IndicesEntity;
import com.cbzf.apis.produkt.repository.ingredients.IngredientsEntity;
import com.cbzf.apis.wartoscodzywcza.repository.NutritionEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecs {

    public static Specification<ProductEntity> hasIdProdukt(Integer idProdukt) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("idProdukt"), idProdukt);
    }

    public static Specification<ProductEntity> hasNazwaProdukt(String nazwaProdukt) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("nazwaProdukt")),
                        "%" + nazwaProdukt.toLowerCase() + "%"
                );
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

    public static Specification<ProductEntity> hasIngredients(String ingredients) {
        return (root, query, criteriaBuilder) -> {
            // Join ProductEntity with IngredientsEntity based on the direct mapping
            Join<ProductEntity, IngredientsEntity> ingredientsJoin = root.join("ingredientsEntity", JoinType.LEFT);

            // Construct a case-insensitive LIKE query on multiple ingredient fields
            Predicate predicate = criteriaBuilder.disjunction(); // Creates a disjunction (OR condition)
            predicate = criteriaBuilder.or(
                    predicate,
                    criteriaBuilder.like(criteriaBuilder.lower(ingredientsJoin.get("skladnik1")), "%" + ingredients.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(ingredientsJoin.get("skladnik2")), "%" + ingredients.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(ingredientsJoin.get("skladnik3")), "%" + ingredients.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(ingredientsJoin.get("skladnik4")), "%" + ingredients.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(ingredientsJoin.get("skladnik5")), "%" + ingredients.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(ingredientsJoin.get("skladnik6")), "%" + ingredients.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(ingredientsJoin.get("skladnik7")), "%" + ingredients.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(ingredientsJoin.get("skladnik8")), "%" + ingredients.toLowerCase() + "%")
            );

            return predicate;
        };
    }

    public static Specification<ProductEntity> hasNutritionName(String nazwa) {
        return (root, query, criteriaBuilder) -> {
            // Avoid duplicate products in results
            query.distinct(true);

            // Join ProductEntity with NutritionEntity
            Join<ProductEntity, NutritionEntity> nutritionJoin = root.join("nutritionEntities", JoinType.LEFT);

            // Predicate for case-insensitive name match
            Predicate nazwaPredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(nutritionJoin.get("nazwa")),
                    "%" + nazwa.toLowerCase() + "%"
            );

            // Predicate for zawartosc not null and greater than zero
            Predicate zawartoscNotNullAndPositive = criteriaBuilder.and(
                    criteriaBuilder.isNotNull(nutritionJoin.get("zawartosc")),
                    criteriaBuilder.gt(nutritionJoin.get("zawartosc"), 0)
            );

            // Combine predicates: require both name match and valid zawartosc
            return criteriaBuilder.and(nazwaPredicate, zawartoscNotNullAndPositive);
        };
    }
}
