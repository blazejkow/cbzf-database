package com.cbzf.apis.produkt.repository.indices;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Indices repository
 */
@Repository
public interface IndicesRepository extends JpaRepository<IndicesEntity, Integer> {
}
