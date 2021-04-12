package com.elvin.latihandto.dto;

import com.elvin.latihandto.validation.NamePrefix;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
@NoArgsConstructor

public class CategoryDTO {

    @NotBlank(message = "Nama kategori wajib diisi!")
    @Column(name = "category_name")
    private String categoryName;
}
