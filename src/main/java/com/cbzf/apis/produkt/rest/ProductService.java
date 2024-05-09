package com.cbzf.apis.produkt.rest;

import com.cbzf.apis.produkt.repository.indices.IndicesEntity;
import com.cbzf.apis.produkt.repository.indices.IndicesRepository;
import com.cbzf.apis.produkt.repository.label.*;
import com.cbzf.apis.produkt.repository.product.ProductSpecs;
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
import org.springframework.data.jpa.domain.Specification;
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
    private final IndicesRepository indicesRepository;

    private final ProductMappers productMappers = new ProductMappers();
    private final IngredientsMappers ingredientsMappers = new IngredientsMappers();
    private final TemporaryProductMappers temporaryProductMappers = new TemporaryProductMappers();
    private final LabelMappers labelMappers = new LabelMappers();


    public void storeFullProduct(List<FullProductInputDTO> input) {
        List<ProductEntity> productEntityList = productMappers.provideEntityFromDto(input);
        productRepository.saveAll(productEntityList);

        List<IngredientsEntity> ingredientsEntityList = ingredientsMappers.provideEntityFromDto(input);
        ingredientsRepository.saveAll(ingredientsEntityList);

        List<LabelEntity> labelEntityList = labelMappers.provideEntityFromDto(input);
        labelRepository.saveAll(labelEntityList);

        List<Integer> savedIds = productEntityList.stream()
                .map(ProductEntity::getIdProdukt)
                .toList();
        removeTemporaryProduct(savedIds);
    }

    public Integer storeTemporaryProduct(List<FullProductInputDTO> input) {
        List<TemporaryProductEntity> temporaryProductEntityList = temporaryProductMappers.provideEntityFromDto(input);
        temporaryProductRepository.saveAll(temporaryProductEntityList);
        return temporaryProductEntityList.get(0).getIdProdukt();
    }

    @Transactional
    public List<ProductEntity> getProducts(String nazwaProdukt, Integer idProdukt, Integer indeksT, String ingredients, String nutritions) {
        Specification<ProductEntity> spec = Specification.where(null);

        if (idProdukt != null) {
            spec = spec.and(ProductSpecs.hasIdProdukt(idProdukt));
        }

        if (indeksT != null) {
            spec = spec.and(ProductSpecs.hasIndeksT(indeksT));
        }

        if (nazwaProdukt != null && !nazwaProdukt.isEmpty()) {
            spec = spec.and(ProductSpecs.hasNazwaProdukt(nazwaProdukt));
        }

        if (ingredients != null && !ingredients.isEmpty()) {
            spec = spec.and(ProductSpecs.hasIngredients(ingredients));
        }

        if (nutritions != null && !nutritions.isEmpty()) {
            spec = spec.and(ProductSpecs.hasNutritionName(nutritions));
        }

        return productRepository.findAll(spec);
    }

    public List<ProductEntity> getProductsForReview() {
        return productRepository.findProductsNotReviewed();
    }


    public List<TemporaryProductEntity> getTemporaryProducts(Integer id, Boolean isApproved) {
        if (id != null && isApproved != null) {
            // Query based on both idDostawca and isApproved
            return temporaryProductRepository.findByIdDostawcaAndApprovedByExpert(id, isApproved);
        } else if (id != null) {
            // Query based on idDostawca only
            return temporaryProductRepository.findByIdDostawca(id);
        } else if (isApproved != null) {
            // Query based on isApproved only
            return temporaryProductRepository.findByApprovedByExpert(isApproved);
        } else {
            // No parameters provided, return all
            return temporaryProductRepository.findAll();
        }
    }

    public List<LabelEntity> getLabel(Integer id) {
        return labelRepository.findByIdProdukt(id);
    }

    public List<IngredientsEntity> getIngredients(Integer id) {
        return ingredientsRepository.findByIdProdukt(id);
    }

    public List<IndicesEntity> getIndices(Integer id) {
        return indicesRepository.findByIdProdukt(id);
    }

    public void removeTemporaryProduct(List<Integer> ids) {
        for (Integer id : ids) {
            temporaryProductRepository.deleteById(id);
        }
    }
}
