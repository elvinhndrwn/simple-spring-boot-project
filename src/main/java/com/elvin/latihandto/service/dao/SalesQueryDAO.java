package com.elvin.latihandto.service.dao;

import com.elvin.latihandto.model.Sales;
import com.elvin.latihandto.model.nativeQuery.GetAllSales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class SalesQueryDAO {

    @Autowired
    private EntityManager entityManager;

    public List<Sales> getAllSales(){
        String queryScript = "SELECT s.id, s.product_id, p.name, s.qty, p.price,  SUM(s.qty*p.price) AS total_price\n" +
                "FROM sales s\n" +
                "JOIN products p ON p.id=s.product_id";

        Query query = entityManager.createNativeQuery(queryScript, "queryGetAllSales");
        List<Sales> list = query.getResultList();
        return list;
    }

    public List<Sales> getCountProduct(int id){
        String queryScript = "SELECT UUID() AS id, p.name, SUM(s.qty) AS qtySold, p.stock\n" +
                "FROM sales s\n" +
                "JOIN products p ON p.id=s.product_id\n" +
                "WHERE p.id = :id";

        Query query = entityManager.createNativeQuery(queryScript, "queryGetCountProduct");
        List<Sales> sales = query.setParameter("id",id).getResultList();

        return sales;
    }
}
