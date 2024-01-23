package com.cbzf.apis.producent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Producent repository
 */
@Repository
public interface ProducentRepository extends JpaRepository<ProducentEntity, Integer> {
    List<ProducentEntity> findByIdProducent(Integer idProducent);
    List<ProducentEntity> findByNipProducent(String nipProducent);
}
