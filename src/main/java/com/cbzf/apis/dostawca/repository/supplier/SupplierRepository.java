package com.cbzf.apis.dostawca.repository.supplier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Supplier repository
 */
@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Integer> {
    List<SupplierEntity> findByIdDostawca(Integer id);
    List<SupplierEntity> findByNipDostawca(String nipDostawca);
}
