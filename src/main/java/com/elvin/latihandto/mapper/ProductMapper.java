package com.elvin.latihandto.mapper;

import com.elvin.latihandto.dto.ProductDTO;
import com.elvin.latihandto.model.Product;

public class ProductMapper {

    // Untuk memetakan objek ProductDTO ke Objek Produk
    public static Product DtoToEntity(ProductDTO prd){
        return  new Product().setName(prd.getName())
                             .setPrice(prd.getPrice())
                             .setStock(prd.getStock())
                             .setCategoryId(prd.getCategoryId());
    }

    // mengonversi objek Produk ke objek ProductDTO
    public  static ProductDTO EntityToDto(Product prd){
        return new ProductDTO().setName(prd.getName())
                               .setPrice(prd.getPrice())
                               .setStock(prd.getStock())
                               .setCategoryId(prd.getCategoryId());
    }
}
