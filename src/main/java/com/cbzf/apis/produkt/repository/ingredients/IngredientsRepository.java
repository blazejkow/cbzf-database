package com.cbzf.apis.produkt.repository.ingredients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Ingredients repository
 */
@Repository
public interface IngredientsRepository extends JpaRepository<IngredientsEntity, Integer> {

    List<IngredientsEntity> findByIdProdukt(Integer id);
}
