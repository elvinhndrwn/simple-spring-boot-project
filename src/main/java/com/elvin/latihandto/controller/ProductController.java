package com.elvin.latihandto.controller;

import com.elvin.latihandto.dto.ProductDTO;
import com.elvin.latihandto.exception.ResourceNotFoundException;
import com.elvin.latihandto.helper.ExcelHelper;
import com.elvin.latihandto.mapper.ProductMapper;
import com.elvin.latihandto.model.Product;
import com.elvin.latihandto.model.nativeQuery.GetAllProduct;
import com.elvin.latihandto.repository.ProductRepository;
import com.elvin.latihandto.response.BookingResponse;
import com.elvin.latihandto.response.ImportResponse;
import com.elvin.latihandto.service.ProductService;
import com.elvin.latihandto.service.dao.ProductQueryDAO;
import com.elvin.latihandto.service.excel.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/prod")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductQueryDAO productQueryDAO;

    @Autowired
    private ExcelService excelService;

    /**
     * METHOD GET
     * @return
     */

    @GetMapping(path = "/products")
    public List<GetAllProduct> products(){
        return productQueryDAO.getAllProduct();
    }

    @GetMapping(path = "/product")
    public ResponseEntity<Product> product(@RequestParam @Min(1) int id){
        Product prd = productService.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Maaf, product dengan ID : " + id + " tidak ditemukan!"));
        return ResponseEntity.ok().body(prd);
    }

    // QUERY CUSTOM DAO
    @GetMapping(path = "/getProduct")
    public ResponseEntity<List<GetAllProduct>> getProduct(@RequestParam @Min(1) int id){
        List<GetAllProduct> list = productQueryDAO.getProductById(id);

        if (list.isEmpty()){

            // Lempar error msg nya
            throw new ResourceNotFoundException("Maaf, product dengan ID : " + id + " tidak ditemukan!");
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * END OFF GET METHOD
     */


    /**
     * POST METHOD
     */

    @PostMapping(path = "/product")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDTO inprod){
        // DTO to Entity
        Product prd = ProductMapper.DtoToEntity(inprod);
        Product addedPrd =productService.save(prd);

//        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                        .path("/{id}")
//                        .buildAndExpand(addedPrd.getId())
//                        .toUri();
//        return ResponseEntity.created(location).build();
        return new ResponseEntity<>(prd, HttpStatus.CREATED);
    }


    @PostMapping(path = "/products")
    public List<Product> createProducts(@Valid @RequestBody List<Product> inprods){
        List<Product> prd = productRepository.saveAll(inprods);
        return prd;
    }

    /**
     * END OF POST METHOD
     */


    /**
     * PUT METHOD
     */
    @PutMapping(path = "/updateProduct")
    public ResponseEntity<List<?>> updateProduct(@RequestParam @Min(1) int id, @Valid @RequestBody ProductDTO upProd){
        // Cari id nya dulu
        Product prd = productService.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Maaf, product dengan ID : " + id + " tidak ditemukan!"));

        // Jika ada ?
        // DTO to Entity
        Product newProd = ProductMapper.DtoToEntity(upProd);

        // dapatkan ID nya
        newProd.setId(prd.getId());

        // Save
        productService.save(newProd);

        // Tampilkan datanya
        List<GetAllProduct> product = productQueryDAO.getProductById(prd.getId());
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    /**
     * END OFF PUT METHOD
     */


    /**
     * DELETE METHOD
     */
    @DeleteMapping(path = "/delProduct")
    public ResponseEntity<String> delProduct(@RequestParam @Min(1) int id){
        Product prd = productService.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Maaf, product dengan ID : " + id + " tidak ditemukan!"));

        productService.delete(prd.getId());
        return ResponseEntity.ok().body("Produk dengan ID : " + id + " telah dihapus!");
    }

    /**
     * END OF DELETE METHOD
     */

    /**
     * SAVE WITH EXCEL FILES
     */

    @PostMapping(path = "/savefile")
    public ResponseEntity<?> savefile(@RequestParam(name = "file") MultipartFile multipartFile){

        // Message for response
        String message = "";

        // Validasi untuk format file nya
        if(ExcelHelper.hasExcelFormat(multipartFile)){
            try {
                excelService.save(multipartFile);

                message = "File berhasil disimpan : " + multipartFile.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ImportResponse(message));
            }catch (Exception e){
                message = "File gagal disimpan : " + multipartFile.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ImportResponse(message));
            }
        }

        message = "Format file tidak sesuai";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ImportResponse(message));
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> getFile() {
        String filename = "products.xlsx";
        InputStreamResource file = new InputStreamResource(excelService.load());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }
}
