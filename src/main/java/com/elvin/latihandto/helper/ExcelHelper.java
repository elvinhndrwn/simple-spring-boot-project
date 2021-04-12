package com.elvin.latihandto.helper;

import com.elvin.latihandto.exception.ResourceNotFoundException;
import com.elvin.latihandto.model.Product;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    static String SHEET = "Products";

    public static boolean hasExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    // Import - Upload

    public static List<Product> excelToProducts(InputStream inputStream){
        String[] HEADERs = { "Id", "Categori_id", "Name", "Price", "Stock" };
            try {
                Workbook workbook = new XSSFWorkbook(inputStream);
                Sheet sheet = workbook.getSheet(SHEET);
                Iterator<Row> rows = sheet.iterator();

                List<Product> products = new ArrayList<Product>();

                int rowNumber = 0;
                while (rows.hasNext()){
                    Row currentRow = rows.next();

                    // Skip Header
                    if (rowNumber == 0) {
                        rowNumber++;
                        continue;
                    }

                    Iterator<Cell> cellsInRow = currentRow.iterator();

                    Product product = new Product();

                    int cellIdx = 0;
                    while (cellsInRow.hasNext()) {
                        Cell currentCell = cellsInRow.next();

                        switch (cellIdx) {
                            case 0:
                                product.setId((int) currentCell.getNumericCellValue());
                                break;

                            case 1:
                                product.setName(currentCell.getStringCellValue());
                                break;

                            case 2:
                                product.setPrice((int) currentCell.getNumericCellValue());
                                break;

                            case 3:
                                product.setStock((int) currentCell.getNumericCellValue());
                                break;

                            case 4:
                                product.setCreatedDate(currentCell.getLocalDateTimeCellValue());
                                break;

                            case 5:
                                product.setUpdatedDate(currentCell.getLocalDateTimeCellValue());
                                break;

                            case 6:
                                product.setCategoryId((int) currentCell.getNumericCellValue());
                                break;



                            default:
                                break;
                        }

                        cellIdx++;
                    }

                    products.add(product);
                }

                workbook.close();
                return products;

            }catch (IOException e){
                    throw new ResourceNotFoundException("Fail to parse excel file : " + e.getMessage());
            }
    }

    // Export - Download
    public static ByteArrayInputStream productsToExcel(List<Product> products) {
        String[] HEADERs = { "Id", "Name", "Price", "Stock", "CreatedDate", "UpdatedDate", "CategoryId" };

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Header
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIdx = 1;
            for (Product product : products) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(product.getId());
                row.createCell(1).setCellValue(product.getName());
                row.createCell(2).setCellValue(product.getPrice());
                row.createCell(3).setCellValue(product.getStock());
                row.createCell(4).setCellValue(product.getCreatedDate());
                row.createCell(5).setCellValue(product.getUpdatedDate());
                row.createCell(6).setCellValue(product.getCategoryId());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}
