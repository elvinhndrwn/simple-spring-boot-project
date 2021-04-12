package com.elvin.latihandto.model.nativeQuery;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@SqlResultSetMapping(name = "queryGetAllSales", entities = {
        @EntityResult(entityClass = GetAllSales.class, fields = {
                @FieldResult(name = "id", column = "id"),
                @FieldResult(name = "productId", column = "product_id"),
                @FieldResult(name = "name", column = "name"),
                @FieldResult(name = "qty", column = "qty"),
                @FieldResult(name = "price", column = "price"),
                @FieldResult(name = "totalPrice", column = "total_price"),
        })
})
public class GetAllSales {

    @Id
    private int id;
    private int productId;
    private String name;
    private int qty;
    private int price;
    private int totalPrice;
}
