package com.cbzf.apis.dostawca.rest;

import com.cbzf.apis.dostawca.repository.supplier.SupplierEntity;
import com.cbzf.apis.dostawca.repository.supplier.SupplierMappers;
import com.cbzf.apis.dostawca.repository.supplier.SupplierRepository;
import com.cbzf.apis.dostawca.repository.temporarysupplier.TemporarySupplierEntity;
import com.cbzf.apis.dostawca.repository.temporarysupplier.TemporarySupplierMappers;
import com.cbzf.apis.dostawca.repository.temporarysupplier.TemporarySupplierRepository;
import com.cbzf.apis.produkt.repository.product.ProductEntity;
import com.cbzf.apis.produkt.repository.product.ProductRepository;
import com.cbzf.apis.user.repository.UserEntity;
import com.cbzf.apis.user.repository.UserRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for Supplier
 */
@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final TemporarySupplierRepository temporarySupplierRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SupplierMappers supplierMappers = new SupplierMappers();
    private final TemporarySupplierMappers temporarySupplierMappers = new TemporarySupplierMappers();

    public void storeSupplier(List<SupplierInputDTO> input) {

        List<SupplierEntity> supplierEntityList = supplierMappers.provideEntityFromDto(input);
        supplierRepository.saveAll(supplierEntityList);

        Integer userId = supplierEntityList.get(0).getIdDostawca();
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found for this id: " + userId));
        userEntity.setIsApproved(true);

        userRepository.save(userEntity);
        List<Integer> savedIds = supplierEntityList.stream()
                .map(SupplierEntity::getIdDostawca)
                .toList();
        removeTemporarySuppliers(savedIds);
    }

    public void storeTemporarySupplier(List<SupplierInputDTO> input) {
        List<TemporarySupplierEntity> temporarySupplierEntityList = temporarySupplierMappers.provideEntityFromDto(input);

        temporarySupplierRepository.saveAll(temporarySupplierEntityList);
    }

    public List<TemporarySupplierEntity> getUnverifiedTemporarySuppliers() {
        List<UserEntity> unverifiedSuppliers = userRepository.findByRoleAndIsApproved("Dostawca", false);
        List<Integer> unverifiedSupplierIds = unverifiedSuppliers.stream()
                .map(UserEntity::getIdUser)
                .collect(Collectors.toList());

        // Fetch TemporarySupplierEntities where idDostawca is in unverifiedSupplierIds
        // Assuming temporarySupplierRepository has a method findByDostawcaIdIn(List<Long> ids)
        return temporarySupplierRepository.findByIdDostawcaIn(unverifiedSupplierIds);
    }

    public List<SupplierEntity> getSupplier(Integer id, String nipDostawca) {
        if (id != null) {
            return supplierRepository.findByIdDostawca(id);
        } else if (nipDostawca != null) {
            return supplierRepository.findByNipDostawca(nipDostawca);
        } else {
            return supplierRepository.findAll();
        }
    }

    public List<TemporarySupplierEntity> getTemporarySupplier(Integer id) {
        if (id != null) {
            return temporarySupplierRepository.findByIdDostawca(id);
        }
        return temporarySupplierRepository.findAll();
    }

    @Transactional
    public void removeTemporarySuppliers(List<Integer> ids) {
        for (Integer id : ids) {
            temporarySupplierRepository.deleteById(id);
        }
    }

    public ByteArrayInputStream generatePdf() {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            List<SupplierEntity> suppliers = supplierRepository.findAll();
            for (SupplierEntity supplier : suppliers) {
                addDostawcaToPdf(document, supplier);
                List<ProductEntity> produkty = productRepository.findByIdDostawca(supplier.getIdDostawca());
                addProduktyToPdf(document, produkty);
                document.add(new Paragraph(" ")); // Add space between different dostawca sections
            }

            document.close();
        } catch (DocumentException ex) {
            // Handle exception
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private void addDostawcaToPdf(Document document, SupplierEntity dostawca) throws DocumentException {
        PdfPTable table = new PdfPTable(2); // Create table with 2 columns
        table.addCell("ID Dostawca");
        table.addCell(String.valueOf(dostawca.getIdDostawca()));
        table.addCell("Nazwa");
        table.addCell(dostawca.getNazwaDostawca());
        table.addCell("RMSD");
        table.addCell(String.valueOf(dostawca.getRmsdDostawca()));
        // Add other fields as needed

        document.add(table);
    }

    private void addProduktyToPdf(Document document, List<ProductEntity> products) throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        table.addCell("ID Produkt");
        table.addCell("Kod EAN");
        table.addCell("Nazwa");

        for (ProductEntity product : products) {
            table.addCell(product.getIdProdukt().toString());
            table.addCell(product.getKodEan());
            table.addCell(product.getNazwaProdukt());
        }

        document.add(table);
    }
}
