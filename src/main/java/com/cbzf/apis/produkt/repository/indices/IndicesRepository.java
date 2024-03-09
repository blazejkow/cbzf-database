package com.cbzf.apis.produkt.repository.indices;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Indices repository
 */
@Repository
public interface IndicesRepository extends JpaRepository<IndicesEntity, Integer> {

    List<IndicesEntity> findByIdProdukt(Integer id);
}
