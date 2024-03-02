package com.cbzf.apis.produkt.repository.temporaryproduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Temporary Product repository
 */
@Repository
public interface TemporaryProductRepository extends JpaRepository<TemporaryProductEntity, Integer> {

    List<TemporaryProductEntity> findByIdDostawca(Integer id);
}
