package com.cbzf.apis.producent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Producent repository
 */
@Repository
public interface ProducentRepository extends JpaRepository<ProducentEntity, Integer> {
}
