package com.cbzf.apis.produkt.rest;

import com.cbzf.apis.produkt.repository.indices.IndicesEntity;
import com.cbzf.apis.produkt.repository.ingredients.IngredientsEntity;
import com.cbzf.apis.produkt.repository.label.LabelEntity;
import com.cbzf.apis.produkt.repository.temporaryproduct.TemporaryProductEntity;
import com.cbzf.apis.produkt.repository.product.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Product controller holding endpoints to get/put data from/into the database
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/cbzf")
@CrossOrigin
public class ProductController {

    private final ProductService service;

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
    public ResponseEntity<Integer> putTemporaryProduct(@RequestBody List<FullProductInputDTO> input) {

        Integer idProdukt = service.storeTemporaryProduct(input);
        return new ResponseEntity<>(idProdukt, HttpStatus.OK);
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
            @RequestParam(required = false) String nazwaProdukt,
            @RequestParam(required = false) Integer indeksT


    ) {
        List<ProductEntity> products = service.getProducts(idDostawca, idKraj, nazwaProdukt, idProdukt, indeksT);
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
    public ResponseEntity<List<TemporaryProductEntity>> getTemporaryProducts(
            @RequestParam(required = false) Integer idDostawca,
            @RequestParam(required = false) Boolean isApproved
    ) {
        List<TemporaryProductEntity> products = service.getTemporaryProducts(idDostawca);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * Endpoint responsible for retrieving the Label for a given idProdukt info from the database.
     * @return response generated after the process of retrieving the records from the database.
     */
    @GetMapping("/get_label")
    public ResponseEntity<List<LabelEntity>> getLabel(
            @RequestParam Integer idProdukt
    ) {
        List<LabelEntity> labels = service.getLabel(idProdukt);
        return new ResponseEntity<>(labels, HttpStatus.OK);
    }

    /**
     * Endpoint responsible for retrieving the Indices for a given idProdukt info from the database.
     * @return response generated after the process of retrieving the records from the database.
     */
    @GetMapping("/get_indices")
    public ResponseEntity<List<IndicesEntity>> getIndices(
            @RequestParam Integer idProdukt
    ) {
        List<IndicesEntity> indices = service.getIndices(idProdukt);
        return new ResponseEntity<>(indices, HttpStatus.OK);
    }

    /**
     * Endpoint responsible for retrieving the Ingredients of a given idProdukt info from the database.
     * @return response generated after the process of retrieving the records from the database.
     */
    @GetMapping("/get_ingredients")
    public ResponseEntity<List<IngredientsEntity>> getIngredients(
            @RequestParam Integer idProdukt
    ) {
        List<IngredientsEntity> ingredients = service.getIngredients(idProdukt);
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }
}
