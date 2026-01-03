package com.poly.rest;

import com.poly.model.*;
import com.poly.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class Lab7RestController {

    @Autowired CategoryService categoryService;
    @Autowired ProductService productService;

    // Đổi StudentService -> AccountService
    @Autowired AccountService accountService;

    // --- BÀI 1: CATEGORY (Giữ nguyên) ---
    @GetMapping("/categories")
    public List<Category> getAllCats() { return categoryService.findAll(); }
    @PostMapping("/categories")
    public Category createCat(@RequestBody Category item) { return categoryService.create(item); }
    @PutMapping("/categories/{id}")
    public Category updateCat(@PathVariable("id") String id, @RequestBody Category item) {
        item.setId(id); return categoryService.update(id, item);
    }
    @DeleteMapping("/categories/{id}")
    public void deleteCat(@PathVariable("id") String id) { categoryService.delete(id); }

    // --- BÀI 2: PRODUCT (Giữ nguyên) ---
    @GetMapping("/products")
    public List<Product> getAllProds() { return productService.findAll(); }
    @PostMapping("/products")
    public Product createProd(@RequestBody Product item) { return productService.create(item); }
    @PutMapping("/products/{id}")
    public Product updateProd(@PathVariable("id") Integer id, @RequestBody Product item) {
        item.setId(id); return productService.update(id, item);
    }
    @DeleteMapping("/products/{id}")
    public void deleteProd(@PathVariable("id") Integer id) { productService.delete(id); }

    // --- BÀI 3: ACCOUNT (Đã sửa lại đúng đề bài) ---
    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        return accountService.findAll();
    }

    @PostMapping("/accounts")
    public Account createAccount(@RequestBody Account item) {
        return accountService.create(item);
    }

    @PutMapping("/accounts/{username}")
    public Account updateAccount(@PathVariable("username") String username, @RequestBody Account item) {
        item.setUsername(username);
        return accountService.update(username, item);
    }

    @DeleteMapping("/accounts/{username}")
    public void deleteAccount(@PathVariable("username") String username) {
        accountService.delete(username);
    }

    // XỬ LÝ NGOẠI LỆ
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}