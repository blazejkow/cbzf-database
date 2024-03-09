package com.cbzf.apis.produkt.repository.product;

import com.cbzf.apis.produkt.repository.nutrition.NutritionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Product repository
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer>, JpaSpecificationExecutor<ProductEntity> {
    List<ProductEntity> findByIdDostawca(Integer id);

    List<NutritionEntity> findByIdProdukt(Integer id);

    @Query("SELECT p FROM ProductEntity p WHERE NOT EXISTS (SELECT 1 FROM ReviewEntity o WHERE o.idProdukt = p.idProdukt)")
    List<ProductEntity> findProductsNotReviewed();
}
