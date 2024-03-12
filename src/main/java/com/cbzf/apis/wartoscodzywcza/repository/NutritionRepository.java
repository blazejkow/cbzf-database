package com.cbzf.apis.wartoscodzywcza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Nutrition repository
 */
@Repository
public interface NutritionRepository extends JpaRepository<NutritionEntity, NutritionPrimaryKey> {
    List<NutritionEntity> findByNutritionPrimaryKeyIdProdukt(Integer id);
}
