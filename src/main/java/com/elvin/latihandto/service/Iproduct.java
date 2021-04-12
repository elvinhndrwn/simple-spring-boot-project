package com.elvin.latihandto.service;

import com.elvin.latihandto.model.Product;

import java.util.List;
import java.util.Optional;

public interface Iproduct {
    List<Product> getAllProduct();
    Optional<Product> findById(int id);
    Product save(Product prd);
    void delete(int id);
}
