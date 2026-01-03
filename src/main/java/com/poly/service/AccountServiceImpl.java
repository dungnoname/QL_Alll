package com.poly.service;

import com.poly.model.Account;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountServiceImpl implements AccountService {
    private static final Map<String, Account> map = new HashMap<>();

    static {
        map.put("admin", new Account("admin", "123", "Nguyễn Admin", "admin@fpt.edu.vn", true, true, "ADMIN"));
        map.put("user", new Account("user", "123", "Trần User", "user@fpt.edu.vn", false, true, "USER"));
        map.put("teonv", new Account("teonv", "123", "Nguyễn Văn Tèo", "teonv@fpt.edu.vn", true, true, "USER"));
    }

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Account findById(String username) {
        return map.get(username);
    }

    @Override
    public Account create(Account account) {
        if (map.containsKey(account.getUsername())) {
            throw new IllegalArgumentException("Username đã tồn tại!");
        }
        map.put(account.getUsername(), account);
        return account;
    }

    @Override
    public Account update(String username, Account account) {
        if (!map.containsKey(username)) {
            throw new IllegalArgumentException("Username không tồn tại!");
        }
        map.put(username, account);
        return account;
    }

    @Override
    public void delete(String username) {
        map.remove(username);
    }
}