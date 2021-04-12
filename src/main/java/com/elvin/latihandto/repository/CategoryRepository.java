package com.elvin.latihandto.repository;

import com.elvin.latihandto.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
