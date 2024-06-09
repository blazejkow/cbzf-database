package com.cbzf.apis.produkt.repository.ingredients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Ingredients repository
 */
@Repository
public interface IngredientsRepository extends JpaRepository<IngredientsEntity, Integer> {

    List<IngredientsEntity> findByIdProdukt(Integer id);
    @Query(value = "WITH columns AS (" +
            "SELECT column_name, ordinal_position " +
            "FROM information_schema.columns " +
            "WHERE table_name = 'sklad' AND column_name != 'id_produkt' " +
            "ORDER BY ordinal_position), " +
            "json_data AS (" +
            "SELECT id_produkt, jsonb_each_text(to_jsonb(sklad) - 'id_produkt') AS kv " +
            "FROM sklad WHERE id_produkt = :idProdukt) " +
            "SELECT 'sklad' AS table_name, c.column_name AS field, (kv).value AS value " +
            "FROM columns c " +
            "JOIN json_data jd ON c.column_name = (kv).key " +
            "WHERE (kv).value IS NOT NULL " +
            "ORDER BY c.ordinal_position", nativeQuery = true)
    List<Object[]> getIngredientsReport(Integer idProdukt);
}
