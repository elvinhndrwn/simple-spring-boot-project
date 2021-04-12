package com.elvin.latihandto.repository;

import com.elvin.latihandto.model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<Sales, Integer> {
}
