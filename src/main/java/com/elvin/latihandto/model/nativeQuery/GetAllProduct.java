package com.elvin.latihandto.model.nativeQuery;

import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;

@Data
@Entity
@SqlResultSetMapping(name = "queryGetAllProduct", entities = {
        @EntityResult(entityClass = GetAllProduct.class, fields = {
                @FieldResult(name = "id", column = "id"),
                @FieldResult(name = "name", column = "name"),
                @FieldResult(name = "price", column = "price"),
                @FieldResult(name = "categoryName", column = "category_name")
        })
})

public class GetAllProduct {
    @Id
    private int id;
    private String name;
    private int price;
    private String categoryName;
}


