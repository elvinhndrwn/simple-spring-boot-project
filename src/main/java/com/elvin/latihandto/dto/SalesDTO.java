package com.elvin.latihandto.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class SalesDTO {

    @Min(value = 1)
    @Column(name = "product_id")
    private int productId;

    @Min(value = 1)
    @Column(name = "qty")
    private int qty;
}
