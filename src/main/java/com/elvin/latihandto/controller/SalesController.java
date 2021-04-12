package com.elvin.latihandto.controller;

import com.elvin.latihandto.exception.ResourceNotFoundException;
import com.elvin.latihandto.model.Product;
import com.elvin.latihandto.model.Sales;
import com.elvin.latihandto.model.nativeQuery.GetAllSales;
import com.elvin.latihandto.repository.SalesRepository;
import com.elvin.latihandto.service.ProductService;
import com.elvin.latihandto.service.SalesService;
import com.elvin.latihandto.service.dao.SalesQueryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/sales")
public class SalesController {

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private SalesService salesService;

    @Autowired
    private SalesQueryDAO salesQueryDAO;

    @Autowired
    private ProductService productService;

    @GetMapping(path = "/all")
    public List<Sales> all(){
        return  salesService.findAll();
    }

    @GetMapping(path = "/countProduct")
    public ResponseEntity<List<?>> countProduct(@RequestParam int id){
        Sales sales = salesService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found"));

        List<Sales> hasil = salesService.countProduct(id);
        return ResponseEntity.ok().body(hasil);
    }
}
