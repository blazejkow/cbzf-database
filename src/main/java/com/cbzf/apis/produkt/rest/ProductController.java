package com.cbzf.apis.produkt.rest;

import com.cbzf.apis.produkt.repository.ProductEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Product controller holding endpoints to get/put data from/into the database
 */
@RestController
@RequestMapping("/cbzf")
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
     * Endpoint responsible for retrieving the Product info from the database.
     * @return response generated after the process of retrieving the records from the database.
     */
    @GetMapping("/produkt")
    public ResponseEntity<List<ProductEntity>> getProducts() {
        List<ProductEntity> products = service.getProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}