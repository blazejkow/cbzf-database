package com.cbzf.apis.ocena.rest;

import com.cbzf.apis.ocena.repository.ReviewEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Review controller holding endpoints to get/put data from/into the database
 */
@RestController
@RequestMapping("/cbzf")
public class ReviewController {

    private final ReviewService service;
    private ReviewController(ReviewService service) {this.service = service;}

    /**
     * Endpoint responsible for storing the Review info in the database
     * @param input - Review record received from the frontend.
     * @return response generated after the process of storing the record into the database.
     */
    @PutMapping("/ocena")
    public ResponseEntity<List<ReviewEntity>> putReview(@RequestBody List<ReviewInputDTO> input) {

        service.storeReview(input);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Endpoint responsible for retrieving the Review info from the database.
     * @return response generated after the process of retrieving the records from the database.
     */
    @GetMapping("/ocena")
    public ResponseEntity<List<ReviewEntity>> getReviews(
            @RequestParam(required = false) Integer id) {
        List<ReviewEntity> reviews = service.getReviews(id);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}
