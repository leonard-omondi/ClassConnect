package com.leo.ClassConnect.service;

import com.leo.ClassConnect.entity.Account;

public interface AccountService {

    Account findAccountByUsername(String username);

    Account registerUser(Account account);

    Account findByUsernameAndPassword(String username, String password);


    Account findAccountById(Long posted_by);
}
