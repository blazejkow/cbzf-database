package com.cbzf.apis.dostawca.repository.temporarysupplier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Temporary Supplier repository
 */
@Repository
public interface TemporarySupplierRepository extends JpaRepository<TemporarySupplierEntity, Integer> {
}
