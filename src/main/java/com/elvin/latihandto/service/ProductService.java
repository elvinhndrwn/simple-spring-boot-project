package com.elvin.latihandto.service;

import com.elvin.latihandto.model.Product;
import com.elvin.latihandto.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements Iproduct{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    @Override
    public Product save(Product prd) {
        return productRepository.save(prd);
    }


    @Override
    public void delete(int id) {
        productRepository.deleteById(id);
    }
}
