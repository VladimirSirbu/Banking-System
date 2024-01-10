package com.example.OnlineBankSystem.service;

import com.example.OnlineBankSystem.exception.AccountNotFoundException;
import com.example.OnlineBankSystem.model.Account;
import com.example.OnlineBankSystem.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account createAccount(Account account) {
        // You can add additional validation or business logic here
        return accountRepository.save(account);
    }

    public Optional<Account> getAccountById(Long id) {
        return Optional.of(accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account with ID: " + id + " not found!")));
    }

    public Set<Account> getAllAccounts() {
        return accountRepository.findAllAccounts();
    }

    public void deleteAccount(Long id) {
        getAccountById(id).ifPresent(account -> accountRepository.deleteById(id));
    }
}