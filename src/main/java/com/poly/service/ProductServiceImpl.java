package com.poly.service;
import com.poly.model.Product;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {
    private static Map<Integer, Product> map = new HashMap<>();

    static {
        map.put(1, new Product(1, "iPhone 15", 25000000.0, new Date(), "DT"));
        map.put(2, new Product(2, "MacBook Pro", 45000000.0, new Date(), "LT"));
    }

    @Override public List<Product> findAll() { return new ArrayList<>(map.values()); }

    @Override public Product create(Product item) {
        // Tự sinh ID nếu null (giả lập Auto Increment)
        if(item.getId() == null) item.setId(map.size() + 1);
        map.put(item.getId(), item);
        return item;
    }

    @Override public Product update(Integer id, Product item) {
        map.put(id, item);
        return item;
    }

    @Override public void delete(Integer id) { map.remove(id); }
}