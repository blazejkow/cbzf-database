package com.cbzf.apis.produkt.rest;

import com.cbzf.apis.produkt.repository.indices.IndicesEntity;
import com.cbzf.apis.produkt.repository.indices.IndicesMappers;
import com.cbzf.apis.produkt.repository.indices.IndicesRepository;
import com.cbzf.apis.produkt.repository.label.LabelEntity;
import com.cbzf.apis.produkt.repository.label.LabelMappers;
import com.cbzf.apis.produkt.repository.label.LabelRepository;
import com.cbzf.apis.produkt.repository.nutrition.NutritionEntity;
import com.cbzf.apis.produkt.repository.nutrition.NutritionMappers;
import com.cbzf.apis.produkt.repository.nutrition.NutritionRepository;
import com.cbzf.apis.produkt.repository.temporaryproduct.TemporaryProductEntity;
import com.cbzf.apis.produkt.repository.temporaryproduct.TemporaryProductMappers;
import com.cbzf.apis.produkt.repository.temporaryproduct.TemporaryProductRepository;
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
    private final LabelRepository labelRepository;
    private final NutritionRepository nutritionRepository;
    private final IndicesRepository indicesRepository;
    private final ProductMappers productMappers = new ProductMappers();
    private final IngredientsMappers ingredientsMappers = new IngredientsMappers();
    private final TemporaryProductMappers temporaryProductMappers = new TemporaryProductMappers();
    private final LabelMappers labelMappers = new LabelMappers();
    private final NutritionMappers nutritionMappers = new NutritionMappers();
    private final IndicesMappers indicesMappers = new IndicesMappers();

    @Transactional
    public void storeFullProduct(List<FullProductInputDTO> input) {
        List<ProductEntity> productEntityList = productMappers.provideEntityFromDto(input);
        productRepository.saveAll(productEntityList);

        List<IngredientsEntity> ingredientsEntityList = ingredientsMappers.provideEntityFromDto(input);
        ingredientsRepository.saveAll(ingredientsEntityList);

        List<LabelEntity> labelEntityList = labelMappers.provideEntityFromDto(input);
        labelRepository.saveAll(labelEntityList);

        List<NutritionEntity> nutritionEntityList = nutritionMappers.provideEntityFromDto(input);
        nutritionRepository.saveAll(nutritionEntityList);

        List<IndicesEntity> indicesEntityList = indicesMappers.provideEntityFromDto(input);
        indicesRepository.saveAll(indicesEntityList);

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
