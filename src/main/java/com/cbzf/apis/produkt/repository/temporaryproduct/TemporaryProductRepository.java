package com.cbzf.apis.produkt.repository.temporaryproduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Temporary Product repository
 */
@Repository
public interface TemporaryProductRepository extends JpaRepository<TemporaryProductEntity, Integer> {
}