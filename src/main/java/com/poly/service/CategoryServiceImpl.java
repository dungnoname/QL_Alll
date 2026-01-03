package com.poly.service;
import com.poly.model.Category;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static Map<String, Category> map = new HashMap<>();

    // Dữ liệu mẫu
    static {
        map.put("DT", new Category("DT", "Điện thoại"));
        map.put("LT", new Category("LT", "Laptop"));
        map.put("DH", new Category("DH", "Đồng hồ"));
    }

    @Override public List<Category> findAll() { return new ArrayList<>(map.values()); }

    @Override public Category create(Category item) {
        if(map.containsKey(item.getId())) throw new IllegalArgumentException("Trùng mã loại!");
        map.put(item.getId(), item);
        return item;
    }

    @Override public Category update(String id, Category item) {
        map.put(id, item);
        return item;
    }

    @Override public void delete(String id) { map.remove(id); }
}