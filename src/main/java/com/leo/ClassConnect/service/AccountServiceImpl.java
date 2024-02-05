package com.leo.ClassConnect.service;

import com.leo.ClassConnect.entity.Account;
import com.leo.ClassConnect.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account findAccountByUsername(String username) {
        return accountRepository.findAccountByUsername(username);
    }

    @Override
    public Account registerUser(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account findByUsernameAndPassword(String username, String password) {
        return accountRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public Account findAccountById(Long posted_by) {
        return accountRepository.findById(posted_by).get();
    }


}
