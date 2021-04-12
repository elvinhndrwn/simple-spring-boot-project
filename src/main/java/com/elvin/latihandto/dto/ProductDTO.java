package com.elvin.latihandto.dto;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.elvin.latihandto.validation.NamePrefix;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@NoArgsConstructor

public class ProductDTO {

    // Anotasi Custom untuk Validation Exception
    @NamePrefix(message = "Name harus dimulai dengan awalan PRDCT")
    @NotBlank(message = "Nama harus di isi !")
    private String name;

    @NotNull
    @Min(value = 1)
    @Positive(message = "Harga tidak boleh 0 atau Negatif !")
    private int price;

    @NotNull
    @Min(value = 1)
    @Positive(message = "Harga tidak boleh 0 atau Negatif !")
    private int stock;

    @NotNull
    @Min(value = 1)
    @Positive(message = "Harga tidak boleh 0 atau Negatif !")
    @Column(name = "category_id")
    private int categoryId;
}
