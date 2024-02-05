/*
   - User Logins
   Users can authenticate their login through a POST request to the endpoint localhost:9000/classconnect/login. The
   request body must include a JSON representation of an Account. A successful login occurs only when the provided
   username and password in the JSON request body match an existing account in the database. In the case of success,
   the response will include a JSON representation of the account, including its account_id, and the HTTP status will
   be 200 OK by default. If the login attempt fails, the response status will be 401 (Unauthorized)

   - User Registration
   Users can create a new Account by making a POST request to the endpoint localhost:9000/classconnect/register. The
   request body should have a JSON representation of an Account without an account_id. Successful registration is
   contingent upon a non-blank username, a password of at least four characters, and the absence of an existing Account
   with the same username. When the criteria are met, the response will contain a JSON representation of the account,
   including its account_id, with a default HTTP status of 200 OK. The new account will be stored in the database.
   If the registration fails due to a duplicate username, the response status will be 409 (Conflict). For any other
   registration-related issues, the response status will be 400

    */
package com.leo.ClassConnect.controller;

import com.leo.ClassConnect.entity.Account;
import com.leo.ClassConnect.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("classconnect")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody Account account) {
        String username = account.getUsername();
        String password = account.getPassword();

        Account existingAccount = accountService.findByUsernameAndPassword(username, password);

        if (existingAccount != null) {
            return ResponseEntity.ok(existingAccount);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        }
    }


    @PostMapping("register")
    public ResponseEntity<Object> registerUser(@RequestBody Account account) {
        if (account.getUsername().isBlank() || account.getPassword().length() < 4) {
            return ResponseEntity.badRequest().body("The username cannot be blank, and the password must be at least 4 characters long.");
        }

        Account existingAccount = accountService.findAccountByUsername(account.getUsername());
        if (existingAccount != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        Account savedUser = accountService.registerUser(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

}
