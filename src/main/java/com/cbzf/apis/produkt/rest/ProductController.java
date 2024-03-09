package com.cbzf.apis.produkt.rest;

import com.cbzf.apis.produkt.repository.nutrition.NutritionEntity;
import com.cbzf.apis.produkt.repository.temporaryproduct.TemporaryProductEntity;
import com.cbzf.apis.produkt.repository.product.ProductEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Product controller holding endpoints to get/put data from/into the database
 */
@RestController
@RequestMapping("/cbzf")
@CrossOrigin
public class ProductController {

    private final ProductService service;
    private ProductController(ProductService service){
        this.service = service;
    }

    /**
     * Endpoint responsible for storing the Product info in the database
     * @param input - Product record received from the frontend.
     * @return response generated after the process of storing the record into the database.
     */
    @PutMapping("/produkt")
    public ResponseEntity<List<ProductEntity>> putProduct(@RequestBody List<FullProductInputDTO> input) {

        service.storeFullProduct(input);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Endpoint responsible for storing the Temporary Product info in the database
     * @param input - Temporary Product record received from the frontend.
     * @return response generated after the process of storing the record into the database.
     */
    @PutMapping("/temporary_produkt")
    public ResponseEntity<List<TemporaryProductEntity>> putTemporaryProduct(@RequestBody List<FullProductInputDTO> input) {

        service.storeTemporaryProduct(input);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Endpoint responsible for retrieving the Product info from the database.
     * @return response generated after the process of retrieving the records from the database.
     */
    @GetMapping("/produkt")
    public ResponseEntity<List<ProductEntity>> getProducts(
            @RequestParam(required = false) Integer idDostawca,
            @RequestParam(required = false) Integer idProdukt,
            @RequestParam(required = false) Integer idKraj,
            @RequestParam(required = false) String nazwaProdukt

    ) {
        List<ProductEntity> products = service.getProducts(idDostawca, idKraj, nazwaProdukt, idProdukt);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * Endpoint responsible for retrieving the Products ready for review
     * @return response generated after the process of retrieving the records from the database.
     */
    @GetMapping("/ready_for_review")
    public ResponseEntity<List<ProductEntity>> getProductsReadyForReview() {
        List<ProductEntity> products = service.getProductsForReview();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * Endpoint responsible for retrieving the Temporary Product info from the database.
     * @return response generated after the process of retrieving the records from the database.
     */
    @GetMapping("/temporary_produkt")
    public ResponseEntity<List<TemporaryProductEntity>> getProdgetTemporaryProducts(
            @RequestParam(required = false) Integer idDostawca
    ) {
        List<TemporaryProductEntity> products = service.getTemporaryProducts(idDostawca);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

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
     * Endpoint responsible for adding the nutrition info about the given productId.
     * @return response generated after the process of retrieving the records from the database.
     */
    @GetMapping("/store_nutrition")
    public ResponseEntity<List<NutritionEntity>> storeNutrition(
            @RequestParam Integer idProdukt
    ) {
        List<NutritionEntity> products = service.getNutrition(idProdukt);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
