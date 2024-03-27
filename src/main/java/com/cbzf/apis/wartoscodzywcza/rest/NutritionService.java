package com.cbzf.apis.wartoscodzywcza.rest;

import com.cbzf.apis.produkt.repository.indices.IndicesEntity;
import com.cbzf.apis.produkt.repository.indices.IndicesMappers;
import com.cbzf.apis.wartoscodzywcza.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for Nutrition matrix
 */
@Service
@RequiredArgsConstructor
public class NutritionService {

    private final NutritionRepository nutritionRepository;
    private final TemporaryNutritionRepository temporaryNutritionRepository;
    private final NutritionMappers nutritionMappers = new NutritionMappers();
    private final IndicesMappers indicesMappers = new IndicesMappers();


    public List<TemporaryNutritionEntity> getTemporaryNutrition(Integer id) {
            return temporaryNutritionRepository.findByNutritionPrimaryKeyIdProdukt(id);

        }

    public List<NutritionEntity> getNutrition(Integer id) {

        return nutritionRepository.findByNutritionPrimaryKeyIdProdukt(id, Sort.by("nutritionPrimaryKey.idNutrient"));
    }

    public void storeNutrition(List<NutritionInputDTO> input) {

        List<NutritionEntity> nutritionEntityList = nutritionMappers.provideEntityFromDto(input);
        nutritionRepository.saveAll(nutritionEntityList);
    }

    public void storeTemporaryNutrition(List<NutritionInputDTO> input) {

        List<TemporaryNutritionEntity> nutritionEntityList = nutritionMappers.provideTemporaryEntityFromDto(input);
        temporaryNutritionRepository.saveAll(nutritionEntityList);
    }

    public IndicesEntity calculateIndices(List<NutritionInputDTO> input) {
        return indicesMappers.calculateIndices(input);
    }
}
