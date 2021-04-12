package com.elvin.latihandto.controller;

import com.elvin.latihandto.dto.CategoryDTO;
import com.elvin.latihandto.exception.ResourceNotFoundException;
import com.elvin.latihandto.mapper.CategoryMapper;
import com.elvin.latihandto.model.Category;
import com.elvin.latihandto.repository.CategoryRepository;
import com.elvin.latihandto.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(path = "/cat")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping(path = "/getAllCat")
    public List<Category> getAllCat(){
        return categoryService.getAllCategory();
    }

    @PostMapping(path = "/addCat")
    public ResponseEntity<Category> addCat( @Valid @RequestBody CategoryDTO inputCat){
        Category cat = CategoryMapper.DtoToEntity(inputCat);
        Category addedCat = categoryService.save(cat);

        return new ResponseEntity<>(cat, HttpStatus.CREATED);
    }

    @PutMapping(path = "/updateCat")
    public ResponseEntity<Category> updateCat(@RequestParam @Min(1) int id, @Valid @RequestBody CategoryDTO upCat){

        // Find id
        Category category = categoryService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maaf, kategori dengan ID = " + id + " tidak ditemukan!"));

        // DTO to Entity
        Category newCat = CategoryMapper.DtoToEntity(upCat);

        //set id
        newCat.setId(category.getId());

        // update n save
        categoryService.save(newCat);

        return ResponseEntity.ok().body(newCat);
    }

    @DeleteMapping(path = "/delCat")
    public ResponseEntity<String> delCat(@RequestParam @Min(1) int id){
        Category category = categoryService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maaf, kategori dengan ID = " + id + " tidak ditemukan!"));

        categoryService.delete(category.getId());
        return new ResponseEntity<>("Categori " +category.getCategorName() + " berhasil dihapus!", HttpStatus.ACCEPTED);
    }
}
