package com.cbzf.apis.produkt.repository.nutrition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Nutrition repository
 */
@Repository
public interface NutritionRepository extends JpaRepository<NutritionEntity, Integer> {
    List<NutritionEntity> findByIdProdukt(Integer id);
}
