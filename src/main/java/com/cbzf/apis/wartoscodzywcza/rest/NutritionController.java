package com.cbzf.apis.wartoscodzywcza.rest;

import com.cbzf.apis.produkt.repository.indices.IndicesEntity;
import com.cbzf.apis.wartoscodzywcza.repository.NutritionEntity;
import com.cbzf.apis.wartoscodzywcza.repository.TemporaryNutritionEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Nutrition controller holding endpoints to get/put data from/into the database
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/cbzf")
@CrossOrigin
public class NutritionController {

    private final NutritionService service;

    /**
     * Endpoint responsible for retrieving the nutrition info about the given productId.
     * @return response generated after the process of retrieving the records from the database.
     */
    @GetMapping("/get_nutrition")
    public ResponseEntity<List<NutritionEntity>> getNutrition(
            @RequestParam Integer idProdukt
    ) {
        List<NutritionEntity> products = service.getNutrition(idProdukt);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * Endpoint responsible for retrieving the temporary nutrition info about the given productId.
     * @return response generated after the process of retrieving the records from the database.
     */
    @GetMapping("/get_temporary_nutrition")
    public ResponseEntity<List<TemporaryNutritionEntity>> getTemporaryNutrition(
            @RequestParam Integer idProdukt
    ) {
        List<TemporaryNutritionEntity> nutrition = service.getTemporaryNutrition(idProdukt);
        return new ResponseEntity<>(nutrition, HttpStatus.OK);
    }

    /**
     * Endpoint responsible for adding the nutrition info about the given productId.
     * @return response generated after the process of retrieving the records from the database.
     */
    @PutMapping("/store_nutrition")
    public ResponseEntity<List<NutritionEntity>> storeNutrition(
            @RequestBody List<NutritionInputDTO> input
    ) {
        service.storeNutrition(input);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/store_temporary_nutrition")
    public ResponseEntity<List<NutritionEntity>> storeTemporaryNutrition(
            @RequestBody List<NutritionInputDTO> input
    ) {
        service.storeTemporaryNutrition(input);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/calculate_indices")
    public ResponseEntity<IndicesEntity> calculateIndices(
            @RequestBody List<NutritionInputDTO> input
    ) {
        IndicesEntity indices = service.calculateIndices(input);
        return new ResponseEntity<>(indices, HttpStatus.OK);
    }
}
