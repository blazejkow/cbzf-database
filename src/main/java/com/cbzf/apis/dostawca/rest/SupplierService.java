package com.cbzf.apis.dostawca.rest;

import com.cbzf.apis.dostawca.repository.supplier.SupplierEntity;
import com.cbzf.apis.dostawca.repository.supplier.SupplierMappers;
import com.cbzf.apis.dostawca.repository.supplier.SupplierRepository;
import com.cbzf.apis.dostawca.repository.temporarysupplier.TemporarySupplierEntity;
import com.cbzf.apis.dostawca.repository.temporarysupplier.TemporarySupplierMappers;
import com.cbzf.apis.dostawca.repository.temporarysupplier.TemporarySupplierRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for Supplier
 */
@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final TemporarySupplierRepository temporarySupplierRepository;
    private final SupplierMappers supplierMappers = new SupplierMappers();
    private final TemporarySupplierMappers temporarySupplierMappers = new TemporarySupplierMappers();

    @Transactional
    public void storeSupplier(List<SupplierInputDTO> input) {
        List<SupplierEntity> supplierEntityList = supplierMappers.provideEntityFromDto(input);
        supplierRepository.saveAll(supplierEntityList);

        List<Integer> savedIds = supplierEntityList.stream()
                .map(SupplierEntity::getIdDostawca)
                .toList();
        removeTemporarySuppliers(savedIds);
    }

    @Transactional
    public void storeTemporarySupplier(List<SupplierInputDTO> input) {
        List<TemporarySupplierEntity> temporarySupplierEntityList = temporarySupplierMappers.provideEntityFromDto(input);
        temporarySupplierRepository.saveAll(temporarySupplierEntityList);
    }

    public List<SupplierEntity> getSupplier(Integer id, String nipDostawca) {
        if (id != null) {
            return supplierRepository.findByIdDostawca(id);
        } else if (nipDostawca != null) {
            return supplierRepository.findByNipDostawca(nipDostawca);
        } else {
            return supplierRepository.findAll();
        }
    }

    public List<TemporarySupplierEntity> getTemporarySupplier() {
        return temporarySupplierRepository.findAll();
    }

    @Transactional
    public void removeTemporarySuppliers(List<Integer> ids) {
        for (Integer id : ids) {
            temporarySupplierRepository.deleteById(id);
        }
    }
}
