package com.example.uploadfile;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory; // Correct import
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductUploadService {
    private final ProductRepository productRepository;

    public String uploadFile(InputStream fileStream) throws IOException {
        List<Products> productsList = new ArrayList<>();

        // Use WorkbookFactory to safely open the input stream
        try (Workbook workbook = WorkbookFactory.create(fileStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                // Skip the Excel Header Row (Row 0)
                if (row.getRowNum() == 0) {
                    continue;
                }

                // Check to make sure the row isn't blank
                if (row.getCell(0) == null) {
                    continue;
                }

                Products product = new Products();

                // Parse Column A (0) - Name
                product.setName(row.getCell(0).getStringCellValue());

                // Parse Column B (1) - Price
                product.setPrice(row.getCell(1).getNumericCellValue());

                // Parse Column C (2) - Quantity
                product.setQuantity((int) row.getCell(2).getNumericCellValue());

                productsList.add(product);
            }

            // Save the populated array to your Postgres database
            productRepository.saveAll(productsList);
        }

        return "Data saved Successfully";
    }
}
