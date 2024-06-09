package com.cbzf.apis.produkt.repository.product;

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

    @Query("SELECT p FROM ProductEntity p WHERE NOT EXISTS (SELECT 1 FROM ReviewEntity o WHERE o.idProdukt = p.idProdukt)")
    List<ProductEntity> findProductsNotReviewed();

    @Query(value = "WITH columns AS (" +
            "SELECT column_name, ordinal_position " +
            "FROM information_schema.columns " +
            "WHERE table_name = 'produkt' AND column_name != 'id_produkt' " +
            "ORDER BY ordinal_position), " +
            "json_data AS (" +
            "SELECT id_produkt, jsonb_each_text(to_jsonb(produkt) - 'id_produkt') AS kv " +
            "FROM produkt WHERE id_produkt = :idProdukt) " +
            "SELECT 'produkt' AS table_name, c.column_name AS field, (kv).value AS value " +
            "FROM columns c " +
            "JOIN json_data jd ON c.column_name = (kv).key " +
            "WHERE (kv).value IS NOT NULL " +
            "ORDER BY c.ordinal_position", nativeQuery = true)
    List<Object[]> getProductReport(Integer idProdukt);
}
