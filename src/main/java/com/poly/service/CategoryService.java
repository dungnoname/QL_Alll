package com.poly.service;
import com.poly.model.Category;
import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category create(Category item);
    Category update(String id, Category item);
    void delete(String id);
}