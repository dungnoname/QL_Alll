package com.poly.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    Integer id;
    String name;
    Double price;
    Date date = new Date(); // Mặc định lấy ngày hiện tại
    String categoryId; // Khóa ngoại tham chiếu sang Category
}