package com.elvin.latihandto.service;

import com.elvin.latihandto.model.Sales;

import java.util.List;
import java.util.Optional;

public interface Isales {
    List<Sales> findAll();
    Sales save(Sales sales);
    Optional<Sales> findById(int id);
    void delete(int id);
    List<Sales> countProduct(int id);

}
