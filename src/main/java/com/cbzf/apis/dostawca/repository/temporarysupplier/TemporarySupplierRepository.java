package com.cbzf.apis.dostawca.repository.temporarysupplier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Temporary Supplier repository
 */
@Repository
public interface TemporarySupplierRepository extends JpaRepository<TemporarySupplierEntity, Integer> {
    List<TemporarySupplierEntity> findByIdDostawca(Integer id);

    List<TemporarySupplierEntity> findByIdDostawcaIn(List<Integer> idDostawcas);
}
