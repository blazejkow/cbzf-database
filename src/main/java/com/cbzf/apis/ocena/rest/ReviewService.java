package com.cbzf.apis.ocena.rest;

import com.cbzf.apis.ocena.repository.ReviewEntity;
import com.cbzf.apis.ocena.repository.ReviewMappers;
import com.cbzf.apis.ocena.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for Review
 */
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMappers reviewMappers = new ReviewMappers();

    @Transactional
    public void storeReview(List<ReviewInputDTO> input) {
        List<ReviewEntity> entityList = reviewMappers.provideEntityFromDto(input);
        reviewRepository.saveAll(entityList);
    }

    public List<ReviewEntity> getReviews(Integer id) {
        if (id != null) {
            return reviewRepository.findByIdProduktAndValueTrue(id); // Filter reviews where value is true
        }
        return reviewRepository.findAll().stream().filter(ReviewEntity::getValue).collect(Collectors.toList());
    }

}
