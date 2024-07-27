package com.cbzf.apis.produkt.rest;

import com.cbzf.apis.ocena.repository.ReviewEntity;
import com.cbzf.apis.ocena.repository.ReviewRepository;
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
import com.itextpdf.text.pdf.PdfPCell;
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
import java.util.*;
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
    private final NutritionRepository nutritionRepository;
    private final ReviewRepository reviewRepository;

    private final ProductMappers productMappers = new ProductMappers();
    private final IngredientsMappers ingredientsMappers = new IngredientsMappers();
    private final TemporaryProductMappers temporaryProductMappers = new TemporaryProductMappers();
    private final LabelMappers labelMappers = new LabelMappers();


    /**
     * Store the full product in the database
     * @param input - list of products to store
     */
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

    /**
     * Get the products from the database
     * @param nazwaProdukt - name of the product
     * @param idProdukt - id of the product
     * @param indeksT - T index of the product
     * @param ingredients - ingredients of the product
     * @param nutritions - nutritions of the product
     * @return - list of products that match the criteria
     */
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

    /**
     * Get the products that are ready for review
     * @return - list of products that are ready for review
     */
    public List<ProductEntity> getProductsForReview() {
        return productRepository.findProductsNotReviewed();
    }


    /**
     * Get the temporary products from the database
     * @param id - id of the supplier to get the products for
     * @param isApproved - whether the product is approved or not
     * @return - list of temporary products
     */
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

    /**
     * Get the label for a product
     * @param id - id of the product to get the label for
     * @return - list of labels for the product
     */
    public List<LabelEntity> getLabel(Integer id) {
        return labelRepository.findByIdProdukt(id);
    }

    /**
     * Get the ingredients for a product
     * @param id - id of the product to get the ingredients for
     * @return - list of ingredients for the product
     */
    public List<IngredientsEntity> getIngredients(Integer id) {
        return ingredientsRepository.findByIdProdukt(id);
    }

    /**
     * Get the indices for a product
     * @param id - id of the product to get the indices for
     * @return - list of indices for the product
     */
    public List<IndicesEntity> getIndices(Integer id) {
        return indicesRepository.findByIdProdukt(id);
    }

    /**
     * Remove temporary products from the database
     * @param ids - list of ids of the products to remove
     */
    public void removeTemporaryProduct(List<Integer> ids) {
        for (Integer id : ids) {
            temporaryProductRepository.deleteById(id);
        }
    }

    /**
     * Get the label image for a product
     * @param idProdukt - id of the product to get the label image for
     * @return - byte array containing the label image
     */
    public byte[] getLabelImage(Integer idProdukt) {
        LabelEntity entity = labelRepository.findById(idProdukt)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return entity.getObraz();
    }

    /**
     * Get the temporary label image for a product
     * @param idProdukt - id of the product to get the label image for
     * @return - byte array containing the label image
     */
    public byte[] getTemporaryLabelImage(Integer idProdukt) {
        TemporaryProductEntity entity = temporaryProductRepository.findById(idProdukt)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return entity.getObraz();
    }

    /**
     * Update the label image for a product
     * @param idProdukt - id of the product to update the label image for
     * @param labelImage - new label image
     * @return - updated product entity
     * @throws IOException - thrown if there is an error while reading the image
     */
    public LabelEntity updateLabelImage(Integer idProdukt, MultipartFile labelImage) throws IOException {
        LabelEntity entity = labelRepository.findById(idProdukt)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        entity.setObraz(labelImage.getBytes());
        return labelRepository.save(entity);
    }

    /**
     * Update the label image for a temporary product
     * @param idProdukt - id of the product to update the label image for
     * @param labelImage - new label image
     * @return - updated temporary product entity
     * @throws IOException - thrown if there is an error while reading the image
     */
    public TemporaryProductEntity updateTemporaryLabelImage(Integer idProdukt, MultipartFile labelImage) throws IOException{
        TemporaryProductEntity entity = temporaryProductRepository.findById(idProdukt)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        entity.setObraz(labelImage.getBytes());
        return temporaryProductRepository.save(entity);
    }

    /**
     * Generate a PDF report for a given product
     * @param id - id of the product to generate the report for
     * @return - ByteArrayInputStream containing the PDF report
     */
    public ByteArrayInputStream generatePdf(Integer id) {
        BaseColor lightBlue = new BaseColor(173, 216, 230);
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
            List<ReviewEntity> reviewResults = reviewRepository.findByIdProdukt(id);

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
            PdfPTable tableNutrition = new PdfPTable(9); // 10 columns for nutrition attributes
            tableNutrition.setWidthPercentage(100);
            tableNutrition.setSpacingBefore(10f);
            tableNutrition.setSpacingAfter(10f);

            // Add table headers
            tableNutrition.addCell(new Paragraph("Nazwa grupy", boldFont));
            tableNutrition.addCell(new Paragraph("Nazwa", boldFont));
            tableNutrition.addCell(new Paragraph("Zawartość", boldFont));
            tableNutrition.addCell(new Paragraph("Jednostka", boldFont));
            tableNutrition.addCell(new Paragraph("% RWS", boldFont));
            tableNutrition.addCell(new Paragraph("Zawartość / Porcja", boldFont));
            tableNutrition.addCell(new Paragraph("% RWS / Porcja", boldFont));
            tableNutrition.addCell(new Paragraph("Indeks", boldFont));
            tableNutrition.addCell(new Paragraph("Legenda", boldFont));

            // Add nutrition results to the table
            for (NutritionEntity nutrition : nutritionResults) {
                tableNutrition.addCell(new Paragraph(nutrition.getNazwaGrupy() != null ? nutrition.getNazwaGrupy() : "", font));
                tableNutrition.addCell(new Paragraph(nutrition.getNazwa() != null ? nutrition.getNazwa() : "", font));
                tableNutrition.addCell(new Paragraph(nutrition.getZawartosc() != null ? nutrition.getZawartosc().toString() : "", font));
                tableNutrition.addCell(new Paragraph(nutrition.getJednostka() != null ? nutrition.getJednostka() : "", font));
                tableNutrition.addCell(new Paragraph(nutrition.getProcentRws() != null ? nutrition.getProcentRws().toString() : "", font));
                tableNutrition.addCell(new Paragraph(nutrition.getZawartoscPorcja() != null ? nutrition.getZawartoscPorcja().toString() : "", font));
                tableNutrition.addCell(new Paragraph(nutrition.getProcentRwsPorcja() != null ? nutrition.getProcentRwsPorcja().toString() : "", font));
                tableNutrition.addCell(createCellWithBackground(nutrition.getIndeks() != null ? nutrition.getIndeks().toString() : "", font, lightBlue));
                tableNutrition.addCell(createCellWithBackground(nutrition.getLegenda() != null ? nutrition.getLegenda() : "", font, lightBlue));
            }

            document.add(tableNutrition);

            // Add the image after the tableNutrition
            LabelEntity labelEntity = labelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Label not found"));
            addLabelImage(document, labelEntity.getObraz());

            // Add Review Results as a table
            document.newPage();
            document.newPage();
            document.add(new Paragraph("Ocena", boldFont));
            PdfPTable tableReview = new PdfPTable(3);
            tableReview.setWidthPercentage(100);
            tableReview.setSpacingBefore(10f);
            tableReview.setSpacingAfter(10f);

            tableReview.addCell(new Paragraph("Grupa", boldFont));
            tableReview.addCell(new Paragraph("Parametr", boldFont));
            tableReview.addCell(new Paragraph("Status", boldFont));

            for (ReviewEntity review : reviewResults) {
                tableReview.addCell(new Paragraph(review.getNazwaGrupa() != null ? review.getNazwaGrupa() : "", font));
                tableReview.addCell(new Paragraph(review.getNazwaPar() != null ? review.getNazwaPar() : "", font));
                tableReview.addCell(createCellWithBackground(review.getValue() != null ? review.getValue().toString() : "", font, lightBlue));
            }

            document.add(tableReview);

            addCertificateText(document, font, boldFont);

            document.close();
        } catch (DocumentException ex) {
            // Handle exception
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    /**
     * Create a cell with a background color
     * @param content - content to be displayed
     * @param font - font to be used
     * @param backgroundColor - background color
     * @return - cell with the content and background color
     */
    private PdfPCell createCellWithBackground(String content, Font font, BaseColor backgroundColor) {
        PdfPCell cell = new PdfPCell(new Paragraph(content, font));
        cell.setBackgroundColor(backgroundColor);
        return cell;
    }

    /**
     * Add the certificate text to the document
     * @param document - document to add the text to
     * @param font - font to be used
     * @param boldFont - bold font to be used
     * @throws DocumentException - thrown if there is an error while adding the text
     */
    private void addCertificateText(Document document, Font font, Font boldFont) throws DocumentException {
        document.add(new Paragraph("Certyfikat Zgodności", boldFont));
        document.add(new Paragraph("Oceny produktu, wyznaczenia indeksów i określenia parametrów (w polach oznaczonych kolorem niebieskim) dokonał ekspert", boldFont));
        document.add(createDottedLineParagraph("imię ", boldFont, document));
        document.add(createDottedLineParagraph("nazwisko ", boldFont, document));
        document.add(createDottedLineParagraph("nazwa Autoryzowanego Laboratorium ", boldFont, document));
        document.add(new Paragraph("\nProdukt spełnia wszelkie wymogi, specyfikacje i posiada cechy wymagane Regulaminem Znaku F-FOOD, w szczególności znajduje się w kategoriach/subkategoriach zaproszonych i wypełnia wszystkie wymogi określone w Kryteriach K1, K2 i K3.\n", font));
        document.add(createDottedLineParagraph("data, podpis, pieczęć ", boldFont, document));
    }

    /**
     * Create a paragraph with a dotted line
     * @param text - text to be displayed
     * @param font - font to be used
     * @param document - document to get the width
     * @return - paragraph with the text and the dotted line
     */
    private Paragraph createDottedLineParagraph(String text, Font font, Document document) {
        float availableWidth = document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin();
        float textWidth = font.getBaseFont().getWidthPoint(text, font.getSize());
        float dotWidth = font.getBaseFont().getWidthPoint(".", font.getSize());

        int numberOfDots = (int) ((availableWidth - textWidth) / dotWidth);

        numberOfDots = Math.max(1, numberOfDots);

        return new Paragraph(text + ".".repeat(numberOfDots), font);
    }

    /**
     * Add a label image to the document
     * @param document - document to add the image to
     * @throws DocumentException - thrown if there is an error while adding the image
     * @throws IOException - thrown if there is an error while decoding the image
     */
    private void addLabelImage(Document document, byte[] imageBytes) throws DocumentException, IOException {
        Image image = Image.getInstance(imageBytes);
        // Scale the image to fit within the document's margins
        float documentWidth = document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin();
        float documentHeight = document.getPageSize().getHeight() - document.topMargin() - document.bottomMargin();
        image.scaleToFit(documentWidth * 0.5f, documentHeight * 0.5f); // Decrease the size to 50%
        document.add(image);
    }
}
