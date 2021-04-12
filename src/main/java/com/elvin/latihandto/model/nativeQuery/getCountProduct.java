package com.elvin.latihandto.model.nativeQuery;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@SqlResultSetMapping(name = "queryGetCountProduct", entities = {
        @EntityResult(entityClass = getCountProduct.class, fields = {
                @FieldResult(name = "id", column = "id"),
                @FieldResult(name = "name", column = "name"),
                @FieldResult(name = "qtySold", column = "qtySold"),
                @FieldResult(name = "stock", column = "stock"),
        })
})
public class getCountProduct {

    @Id
    private String id;
    private String name;
    private int qtySold;
    private int stock;

}
