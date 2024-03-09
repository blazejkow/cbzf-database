package com.cbzf.apis.wartoscodzywcza.rest;

import com.cbzf.apis.wartoscodzywcza.repository.*;
import lombok.RequiredArgsConstructor;
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


    public List<TemporaryNutritionEntity> getTemporaryNutrition(Integer id) {
            return temporaryNutritionRepository.findByIdProdukt(id);

        }

    public List<NutritionEntity> getNutrition(Integer id) {

        return nutritionRepository.findByIdProdukt(id);
    }

    public void storeNutrition(List<NutritionInputDTO> input) {

        List<NutritionEntity> nutritionEntityList = nutritionMappers.provideEntityFromDto(input);
        nutritionRepository.saveAll(nutritionEntityList);
    }

    public void storeTemporaryNutrition(List<NutritionInputDTO> input) {

        List<TemporaryNutritionEntity> nutritionEntityList = nutritionMappers.provideTemporaryEntityFromDto(input);
        temporaryNutritionRepository.saveAll(nutritionEntityList);
    }
}
