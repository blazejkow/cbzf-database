package com.cbzf.apis.producent.rest;

import com.cbzf.apis.producent.repository.ProducentEntity;
import com.cbzf.apis.producent.repository.ProducentMappers;
import com.cbzf.apis.producent.repository.ProducentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for Producent
 */
@Service
@RequiredArgsConstructor
public class ProducentService {

    private final ProducentRepository repository;
    private final ProducentMappers mappers = new ProducentMappers();

    @Transactional
    public void storeProducent(List<ProducentInputDTO> input) {
        List<ProducentEntity> entityList = mappers.provideEntityFromDto(input);
        repository.saveAll(entityList);
    }
}
