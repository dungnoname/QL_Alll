package com.poly.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    String username;
    String password;
    String fullname;
    String email;
    Boolean photo = false;
    Boolean enabled = true;
    String role; // "USER" hoặc "ADMIN"
}