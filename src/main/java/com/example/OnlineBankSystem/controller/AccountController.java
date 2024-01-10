package com.example.OnlineBankSystem.controller;

import com.example.OnlineBankSystem.model.Account;
import com.example.OnlineBankSystem.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

/**
 * 1. Create/Delete(close) account
 * 2. Activate/Disable account
 */

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/new")
    public ResponseEntity<String> createAccount(@RequestBody Account account) {
        Account createdAccount = accountService.createAccount(account);
        return ResponseEntity.ok("Account created with ID: " + createdAccount.getId());
    }

    @GetMapping
    public ResponseEntity<Set<Account>> getAllAccounts() {
        return ResponseEntity.of(Optional.ofNullable(accountService.getAllAccounts()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        return ResponseEntity.of(accountService.getAccountById(id));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account with ID: " + id + " deleted successfully");
    }
}
