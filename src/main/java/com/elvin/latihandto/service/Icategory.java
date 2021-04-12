package com.elvin.latihandto.service;

import com.elvin.latihandto.model.Category;

import java.util.List;
import java.util.Optional;

public interface Icategory {
    List<Category> getAllCategory();
    Category save(Category category);
    Optional<Category> findById(int id);
    void delete(int id);
}
