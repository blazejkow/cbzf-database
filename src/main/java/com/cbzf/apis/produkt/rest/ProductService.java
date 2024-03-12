package com.cbzf.apis.produkt.rest;

import com.cbzf.apis.produkt.repository.indices.IndicesEntity;
import com.cbzf.apis.produkt.repository.indices.IndicesMappers;
import com.cbzf.apis.produkt.repository.indices.IndicesRepository;
import com.cbzf.apis.produkt.repository.label.LabelEntity;
import com.cbzf.apis.produkt.repository.label.LabelMappers;
import com.cbzf.apis.produkt.repository.label.LabelRepository;
import com.cbzf.apis.produkt.repository.product.ProductSpecifications;
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
import java.util.Objects;

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
    private final IndicesMappers indicesMappers = new IndicesMappers();


    public void storeFullProduct(List<FullProductInputDTO> input) {
        List<ProductEntity> productEntityList = productMappers.provideEntityFromDto(input);
        productRepository.saveAll(productEntityList);

        List<IngredientsEntity> ingredientsEntityList = ingredientsMappers.provideEntityFromDto(input);
        ingredientsRepository.saveAll(ingredientsEntityList);

        List<LabelEntity> labelEntityList = labelMappers.provideEntityFromDto(input);
        labelRepository.saveAll(labelEntityList);

        List<IndicesEntity> indicesEntityList = indicesMappers.provideEntityFromDto(input);
        for (IndicesEntity entity : indicesEntityList) {
            // Calculate the sum of all indeks fields except indeksT
            int sum = calculateSumOfIndices(entity);
            entity.setIndeksT(sum);  // Set the sum to indeksT
        }
        indicesRepository.saveAll(indicesEntityList);

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

    public List<ProductEntity> getProducts(Integer idDostawca, Integer idKraj, String nazwaProdukt, Integer idProdukt, Integer indeksT) {
        Specification<ProductEntity> spec = Specification.where(null);

        if (idDostawca != null) {
            spec = spec.and(ProductSpecifications.hasIdDostawca(idDostawca));
        }

        if (idProdukt != null) {
            spec = spec.and(ProductSpecifications.hasIdProdukt(idProdukt));
        }

        if (idKraj != null) {
            spec = spec.and(ProductSpecifications.hasIdKraj(idKraj));
        }

        if (indeksT != null) {
            spec = spec.and(ProductSpecifications.hasIndeksT(indeksT));
        }

        if (nazwaProdukt != null && !nazwaProdukt.isEmpty()) {
            spec = spec.and(ProductSpecifications.hasNazwaProdukt(nazwaProdukt));
        }

        return productRepository.findAll(spec);
    }

    public List<ProductEntity> getProductsForReview() {
        return productRepository.findProductsNotReviewed();
    }


    public List<TemporaryProductEntity> getTemporaryProducts(Integer id) {
        if (id != null) {
            return temporaryProductRepository.findByIdDostawca(id);
        }
        return temporaryProductRepository.findAll(); }

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

    private int calculateSumOfIndices(IndicesEntity entity) {
        return (entity.getIndeksE() == null ? 0 : entity.getIndeksE()) +
                (entity.getIndeksV() == null ? 0 : entity.getIndeksV()) +
                (entity.getIndeksM() == null ? 0 : entity.getIndeksM()) +
                (entity.getIndeksO() == null ? 0 : entity.getIndeksO()) +
                (entity.getIndeksF() == null ? 0 : entity.getIndeksF()) +
                (entity.getIndeksP() == null ? 0 : entity.getIndeksP()) +
                (entity.getIndeksS() == null ? 0 : entity.getIndeksS());
    }
}




