package com.cbzf.apis.produkt.repository.ingredients;

import com.cbzf.apis.produkt.repository.ingredients.IngredientsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Ingredients repository
 */
@Repository
public interface IngredientsRepository extends JpaRepository<IngredientsEntity, Integer> {
}
