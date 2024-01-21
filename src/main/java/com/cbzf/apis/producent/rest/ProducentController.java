package com.cbzf.apis.producent.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Producent controller holding endpoints to get/put data from/into the database
 */
@RestController
@RequestMapping("/cbzf")
public class ProducentController {

    private final ProducentService service;
    private ProducentController(ProducentService service){
        this.service = service;
    }

    /**
     * @param input - Producent record received from the frontend.
     * @return response generated after the process of storing the record into the database.
     */
    @PutMapping("/producent")
    public ResponseEntity putProducent(@RequestBody List<ProducentInputDTO> input) {

        service.storeProducent(input);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
