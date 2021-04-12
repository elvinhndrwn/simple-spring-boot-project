package com.elvin.latihandto.service.dao;

import com.elvin.latihandto.model.Product;
import com.elvin.latihandto.model.nativeQuery.GetAllProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.awt.geom.QuadCurve2D;
import java.util.List;

@Service
public class ProductQueryDAO  {

    @Autowired
    private EntityManager entityManager;

    public List<GetAllProduct> getAllProduct(){
        String queryScript = "SELECT p.id, p.name, p.price, c.category_name \n" +
                                "FROM products p\n" +
                                "JOIN categories c ON c.id=p.category_id";

        Query q = entityManager.createNativeQuery(queryScript, "queryGetAllProduct");
        List<GetAllProduct> list = q.getResultList();

        return list;
    }

    public List<GetAllProduct> getProductById(int id){
        String queryScript = "SELECT p.id, p.name, p.price, c.category_name \n" +
                "FROM products p JOIN categories c ON c.id=p.category_id\n" +
                "WHERE p.id = :id";
        Query q = entityManager.createNativeQuery(queryScript, "queryGetAllProduct");
        List<GetAllProduct> list = q.setParameter("id", id).getResultList();

        return list;
    }
}
