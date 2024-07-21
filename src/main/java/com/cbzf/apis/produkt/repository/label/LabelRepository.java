package com.cbzf.apis.produkt.repository.label;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Label repository
 */
@Repository
public interface LabelRepository extends JpaRepository<LabelEntity, Integer> {
    List<LabelEntity> findByIdProdukt(Integer id);

    @Query(value = "WITH columns AS (" +
            "SELECT column_name, ordinal_position " +
            "FROM information_schema.columns " +
            "WHERE table_name = 'etykieta' AND column_name != 'id_produkt' " +
            "ORDER BY ordinal_position), " +
            "json_data AS (" +
            "SELECT id_produkt, jsonb_each_text(to_jsonb(etykieta) - 'id_produkt') AS kv " +
            "FROM etykieta WHERE id_produkt = :idProdukt) " +
            "SELECT 'etykieta' AS table_name, c.column_name AS field, (kv).value AS value " +
            "FROM columns c " +
            "JOIN json_data jd ON c.column_name = (kv).key " +
            "WHERE (kv).value IS NOT NULL " +
            "ORDER BY c.ordinal_position", nativeQuery = true)
    List<Object[]> getLabelReport(Integer idProdukt);
}
