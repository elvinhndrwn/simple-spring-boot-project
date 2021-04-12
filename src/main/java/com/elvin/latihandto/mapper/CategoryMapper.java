package com.elvin.latihandto.mapper;

import com.elvin.latihandto.dto.CategoryDTO;
import com.elvin.latihandto.model.Category;

public class CategoryMapper {

    public  static Category DtoToEntity(CategoryDTO cat){
        return new Category().setCategorName(cat.getCategoryName());
    }

    public static CategoryDTO EntityToDto(Category cat){
        return new CategoryDTO().setCategoryName(cat.getCategorName());
    }
}
