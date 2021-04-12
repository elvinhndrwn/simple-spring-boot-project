package com.elvin.latihandto.service;

import com.elvin.latihandto.model.Sales;
import com.elvin.latihandto.repository.SalesRepository;
import com.elvin.latihandto.service.dao.SalesQueryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalesService implements Isales{
    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private SalesQueryDAO salesQueryDAO;

    @Override
    public List<Sales> findAll() {
        return salesQueryDAO.getAllSales();
    }

    @Override
    public Sales save(Sales sales) {
        return salesRepository.save(sales);
    }

    @Override
    public Optional<Sales> findById(int id) {
        return salesRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        salesRepository.deleteById(id);
    }

    @Override
    public List<Sales> countProduct(int id) {
        return salesQueryDAO.getCountProduct(id);
    }
}
