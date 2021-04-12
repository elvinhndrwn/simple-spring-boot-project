package com.elvin.latihandto.model.nativeQuery;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@SqlResultSetMapping(name = "queryGetProdcutName", entities = {
        @EntityResult(entityClass = GetProductName.class, fields = {
                @FieldResult(name = "productName", column = "product_name"),
                @FieldResult(name = "stock", column = "stock"),
        })
})
public class GetProductName {
    @Id
    @Column(name = "product_name")
    private String productName;

    @Column(name = "stock")
    private int stock;
}
