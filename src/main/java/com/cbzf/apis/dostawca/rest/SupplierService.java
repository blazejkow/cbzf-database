package com.cbzf.apis.dostawca.rest;

import com.cbzf.apis.dostawca.repository.SupplierEntity;
import com.cbzf.apis.dostawca.repository.SupplierMappers;
import com.cbzf.apis.dostawca.repository.SupplierRepository;
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

    private final SupplierRepository repository;
    private final SupplierMappers mappers = new SupplierMappers();

    @Transactional
    public void storeSupplier(List<SupplierInputDTO> input) {
        List<SupplierEntity> entityList = mappers.provideEntityFromDto(input);
        repository.saveAll(entityList);
    }

    public List<SupplierEntity> getSuppliers(Integer id, String nipDostawca) {
        if (id != null) {
            return repository.findByIdDostawca(id);
        } else if (nipDostawca != null) {
            return repository.findByNipDostawca(nipDostawca);
        } else {
            return repository.findAll();
        }
    }
}
