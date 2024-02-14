package com.cbzf.apis.produkt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Product repository
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
}
