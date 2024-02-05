package com.leo.ClassConnect.repository;

import com.leo.ClassConnect.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findAccountByUsername(String username);

    Account findByUsernameAndPassword(String username, String password);



}
