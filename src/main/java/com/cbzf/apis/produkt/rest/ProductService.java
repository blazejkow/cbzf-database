package com.cbzf.apis.produkt.rest;

import com.cbzf.apis.produkt.repository.fullproduct.TemporaryProductEntity;
import com.cbzf.apis.produkt.repository.fullproduct.TemporaryProductMappers;
import com.cbzf.apis.produkt.repository.fullproduct.TemporaryProductRepository;
import com.cbzf.apis.produkt.repository.ingredients.IngredientsEntity;
import com.cbzf.apis.produkt.repository.ingredients.IngredientsMappers;
import com.cbzf.apis.produkt.repository.ingredients.IngredientsRepository;
import com.cbzf.apis.produkt.repository.product.ProductEntity;
import com.cbzf.apis.produkt.repository.product.ProductMappers;
import com.cbzf.apis.produkt.repository.product.ProductRepository;
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
    private final TemporaryProductRepository temporaryProductRepository;
    private final ProductMappers productMappers = new ProductMappers();
    private final IngredientsMappers ingredientsMappers = new IngredientsMappers();
    private final TemporaryProductMappers temporaryProductMappers = new TemporaryProductMappers();

    @Transactional
    public void storeFullProduct(List<FullProductInputDTO> input) {
        List<ProductEntity> productEntityList = productMappers.provideEntityFromDto(input);
        productRepository.saveAll(productEntityList);

        List<IngredientsEntity> ingredientsEntityList = ingredientsMappers.provideEntityFromDto(input);
        ingredientsRepository.saveAll(ingredientsEntityList);
    }

    @Transactional
    public void storeTemporaryProduct(List<FullProductInputDTO> input) {
        List<TemporaryProductEntity> temporaryProductEntityList = temporaryProductMappers.provideEntityFromDto(input);
        temporaryProductRepository.saveAll(temporaryProductEntityList);
    }

    public List<ProductEntity> getProducts(Integer id) {
        if (id != null) {
            return productRepository.findByIdDostawca(id);
        }
        return productRepository.findAll();
    }
    public List<TemporaryProductEntity> getTemporaryProducts() { return temporaryProductRepository.findAll(); }
}
