package com.cbzf.apis.producent.rest;

import com.cbzf.apis.producent.repository.ProducentEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<ProducentEntity>> putProducent(@RequestBody List<ProducentInputDTO> input) {

        service.storeProducent(input);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @param id one of the parameters the retrieved table can be filtered by.
     * @param nip one of the parameters the retrieved table can be filtered by.
     * @return response generated after the process of retrieving the records from the database.
     */
    @GetMapping("/producent")
    public ResponseEntity<List<ProducentEntity>> getProducents(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String nip
    ) {
        List<ProducentEntity> producents = service.getProducents(id, nip);
        return new ResponseEntity<>(producents, HttpStatus.OK);
    }
}
