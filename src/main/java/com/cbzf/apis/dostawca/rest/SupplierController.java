package com.cbzf.apis.dostawca.rest;

import com.cbzf.apis.dostawca.repository.supplier.SupplierEntity;
import com.cbzf.apis.dostawca.repository.temporarysupplier.TemporarySupplierEntity;
import com.cbzf.apis.user.repository.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Supplier controller holding endpoints to get/put data from/into the database
 */
@RestController
@RequestMapping("/cbzf")
@CrossOrigin
public class SupplierController {

    private final SupplierService service;
    private SupplierController(SupplierService service){
        this.service = service;
    }

    /**
     * @param input - Supplier record received from the frontend.
     * @return response generated after the process of storing the record into the database.
     */
    @PutMapping("/dostawca")
    public ResponseEntity<List<SupplierEntity>> putSupplier(@RequestBody List<SupplierInputDTO> input) {

        service.storeSupplier(input);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @param input - Temporary Supplier record received from the frontend.
     * @return response generated after the process of storing the record into the database.
     */
    @PutMapping("/dostawca_temporary")
    public ResponseEntity<List<TemporarySupplierEntity>> putTemporarySupplier(@RequestBody List<SupplierInputDTO> input) {

        service.storeTemporarySupplier(input);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get_unverified_suppliers")
    public ResponseEntity<List<TemporarySupplierEntity>> getUnverifiedSuppliers() {
        List<TemporarySupplierEntity> users = service.getUnverifiedTemporarySuppliers();
        return ResponseEntity.ok(users);
    }

    /**
     * @param id one of the parameters the retrieved table can be filtered by.
     * @param nip one of the parameters the retrieved table can be filtered by.
     * @return response generated after the process of retrieving the records from the database.
     */
    @GetMapping("/dostawca")
    public ResponseEntity<List<SupplierEntity>> getSupplier(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String nip
    ) {
        List<SupplierEntity> suppliers = service.getSupplier(id, nip);
        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }

    /**
     * @return response generated after the process of retrieving the records from the database.
     */
    @GetMapping("/dostawca_temporary")
    public ResponseEntity<List<TemporarySupplierEntity>> getTemporarySupplier(
            @RequestParam(required = false) Integer idDostawca
    ) {
        List<TemporarySupplierEntity> suppliers = service.getTemporarySupplier(idDostawca);
        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }
}
