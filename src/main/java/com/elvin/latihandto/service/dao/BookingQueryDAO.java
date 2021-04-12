package com.elvin.latihandto.service.dao;

import com.elvin.latihandto.model.nativeQuery.GetProductName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class BookingQueryDAO {
    @Autowired
    private EntityManager entityManager;

    public List<GetProductName> getProductName(int id){
        String queryScript= "SELECT p.name AS product_name, p.stock FROM products p WHERE p.id = :id";

        Query query = entityManager.createNativeQuery(queryScript, "queryGetProdcutName");
        List<GetProductName> productName = query.setParameter("id", id).getResultList();

       return productName;

    }
}
