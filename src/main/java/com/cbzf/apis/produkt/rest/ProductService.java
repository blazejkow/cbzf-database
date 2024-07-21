package com.cbzf.apis.produkt.rest;

import com.cbzf.apis.produkt.repository.indices.IndicesEntity;
import com.cbzf.apis.produkt.repository.indices.IndicesRepository;
import com.cbzf.apis.produkt.repository.label.LabelEntity;
import com.cbzf.apis.produkt.repository.label.LabelRepository;
import com.cbzf.apis.produkt.repository.label.LabelMappers;
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
import com.cbzf.apis.wartoscodzywcza.repository.NutritionEntity;
import com.cbzf.apis.wartoscodzywcza.repository.NutritionRepository;
import com.cbzf.exceptions.ResourceNotFoundException;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Base64;
import java.util.Map;
import java.util.HashMap;


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
    private final NutritionRepository nutritionRepository;

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
            List<Integer> productIds = nutritionRepository.findDistinctIdProduktByNazwaContaining(nutritions);
            if (!productIds.isEmpty()) {
                spec = spec.and((root, query, criteriaBuilder) -> root.get("idProdukt").in(productIds));
            } else {
                return new ArrayList<>();
            }
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

    public byte[] getLabelImage(Integer idProdukt) {
        LabelEntity entity = labelRepository.findById(idProdukt)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return entity.getObraz();
    }

    public LabelImageDTO getTemporaryLabelImage(Integer idProdukt) {
        TemporaryProductEntity entity = temporaryProductRepository.findById(idProdukt)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        String base64Image = Base64.getEncoder().encodeToString(entity.getObraz());
        return new LabelImageDTO(entity.getIdProdukt(), base64Image);
    }


    public LabelEntity updateLabelImage(Integer idProdukt, MultipartFile labelImage) throws IOException {
        LabelEntity entity = labelRepository.findById(idProdukt)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        entity.setObraz(labelImage.getBytes());
        return labelRepository.save(entity);
    }

    public TemporaryProductEntity updateTemporaryLabelImage(Integer idProdukt, MultipartFile labelImage) throws IOException{
        TemporaryProductEntity entity = temporaryProductRepository.findById(idProdukt)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        entity.setObraz(labelImage.getBytes());
        return temporaryProductRepository.save(entity);
    }

    public ByteArrayInputStream generatePdf(Integer id) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            BaseFont baseFont = BaseFont.createFont("DejaVuSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(baseFont);
            Font boldFont = new Font(baseFont, Font.DEFAULTSIZE, Font.BOLD);

            List<Object[]> productResults = productRepository.getProductReport(id);
            List<Object[]> ingredientsResults = ingredientsRepository.getIngredientsReport(id);
            List<Object[]> labelResults = labelRepository.getLabelReport(id);
            List<NutritionEntity> nutritionResults = nutritionRepository.findByNutritionPrimaryKeyIdProdukt(id, Sort.by("nutritionPrimaryKey.idNutrient"));

            Map<String, List<Object[]>> groupedResults = new HashMap<>();
            groupedResults.put("Informacje podstawowe", productResults);
            groupedResults.put("Skład", ingredientsResults);
            groupedResults.put("Etykieta", labelResults);

            for (Map.Entry<String, List<Object[]>> entry : groupedResults.entrySet()) {
                String tableName = entry.getKey();
                List<Object[]> results = entry.getValue();

                document.add(new Paragraph(tableName, boldFont));
                for (Object[] row : results) {
                    String field = (String) row[1];
                    String value = (String) row[2];

                    document.add(new Paragraph(field + ": " + value, font));
                }
                document.add(new Paragraph(" ", font));
            }

            // Add Nutrition Results as a table
            document.add(new Paragraph("Wartość odżywcza", boldFont));
            PdfPTable table = new PdfPTable(9); // 10 columns for nutrition attributes
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Add table headers
            table.addCell(new Paragraph("Nazwa grupy", boldFont));
            table.addCell(new Paragraph("Nazwa", boldFont));
            table.addCell(new Paragraph("Zawartość", boldFont));
            table.addCell(new Paragraph("Jednostka", boldFont));
            table.addCell(new Paragraph("% RWS", boldFont));
            table.addCell(new Paragraph("Zawartość / Porcja", boldFont));
            table.addCell(new Paragraph("% RWS / Porcja", boldFont));
            table.addCell(new Paragraph("Indeks", boldFont));
            table.addCell(new Paragraph("Legenda", boldFont));

            // Add nutrition results to the table
            for (NutritionEntity nutrition : nutritionResults) {
                table.addCell(new Paragraph(nutrition.getNazwaGrupy() != null ? nutrition.getNazwaGrupy() : "", font));
                table.addCell(new Paragraph(nutrition.getNazwa() != null ? nutrition.getNazwa() : "", font));
                table.addCell(new Paragraph(nutrition.getZawartosc() != null ? nutrition.getZawartosc().toString() : "", font));
                table.addCell(new Paragraph(nutrition.getJednostka() != null ? nutrition.getJednostka() : "", font));
                table.addCell(new Paragraph(nutrition.getProcentRws() != null ? nutrition.getProcentRws().toString() : "", font));
                table.addCell(new Paragraph(nutrition.getZawartoscPorcja() != null ? nutrition.getZawartoscPorcja().toString() : "", font));
                table.addCell(new Paragraph(nutrition.getProcentRwsPorcja() != null ? nutrition.getProcentRwsPorcja().toString() : "", font));
                table.addCell(new Paragraph(nutrition.getIndeks() != null ? nutrition.getIndeks().toString() : "", font));
                table.addCell(new Paragraph(nutrition.getLegenda() != null ? nutrition.getLegenda() : "", font));
            }

            document.add(table);

            document.close();
        } catch (DocumentException ex) {
            // Handle exception
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
