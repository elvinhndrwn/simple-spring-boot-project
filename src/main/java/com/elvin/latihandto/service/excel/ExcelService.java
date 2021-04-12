package com.elvin.latihandto.service.excel;

import com.elvin.latihandto.exception.ResourceNotFoundException;
import com.elvin.latihandto.helper.ExcelHelper;
import com.elvin.latihandto.model.Product;
import com.elvin.latihandto.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {
    @Autowired
    ProductRepository productRepository;

    public  void save (MultipartFile multipartFile){
        try{
            List<Product> products = ExcelHelper.excelToProducts(multipartFile.getInputStream());
            productRepository.saveAll(products);
        }catch (IOException e){
            throw new ResourceNotFoundException("Fail to store excel data, " + e.getMessage() );
        }
    }

    public List<Product> getAllTutorials() {
        return productRepository.findAll();
    }

    public ByteArrayInputStream load() {
        List<Product> products = productRepository.findAll();

        ByteArrayInputStream in = ExcelHelper.productsToExcel(products);
        return in;
    }
}
