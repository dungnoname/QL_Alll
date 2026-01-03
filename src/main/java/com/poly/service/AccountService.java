package com.poly.service;

import com.poly.model.Account;
import java.util.List;

public interface AccountService {
    List<Account> findAll();
    Account findById(String username);
    Account create(Account account);
    Account update(String username, Account account);
    void delete(String username);
}