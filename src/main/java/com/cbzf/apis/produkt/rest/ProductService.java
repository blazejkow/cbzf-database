package com.cbzf.apis.produkt.rest;

import com.cbzf.apis.produkt.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for Product related objects
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final IngredientsRepository ingredientsRepository;
    private final ProductMappers productMappers = new ProductMappers();
    private final IngredientsMappers ingredientsMappers = new IngredientsMappers();

    @Transactional
    public void storeFullProduct(List<FullProductInputDTO> input) {
        List<ProductEntity> productEntityList = productMappers.provideEntityFromDto(input);
        productRepository.saveAll(productEntityList);

        List<IngredientsEntity> ingredientsEntityList = ingredientsMappers.provideEntityFromDto(input);
        ingredientsRepository.saveAll(ingredientsEntityList);

    }

    public List<ProductEntity> getProducts() {
        return productRepository.findAll();
    }
}
