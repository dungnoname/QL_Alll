package com.poly.service;
import com.poly.model.Product;
import java.util.List;

public interface ProductService {
    List<Product> findAll();
    Product create(Product item);
    Product update(Integer id, Product item);
    void delete(Integer id);
}