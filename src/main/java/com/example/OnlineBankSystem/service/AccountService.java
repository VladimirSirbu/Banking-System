package com.example.OnlineBankSystem.service;

import com.example.OnlineBankSystem.exception.AccountNotFoundException;
import com.example.OnlineBankSystem.model.Account;
import com.example.OnlineBankSystem.repository.AccountRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.OnlineBankSystem.service.utilis.OnlineBankSystemConstants.MY_SQL_CIRCUIT_BREAKER;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Optional<Account> getAccountById(Long id) {
        return Optional.of(accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account with ID: " + id + " not found!")));
    }

    @CircuitBreaker(name = MY_SQL_CIRCUIT_BREAKER)
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public void deleteAccount(Long id) {
        getAccountById(id).ifPresent(account -> accountRepository.deleteById(id));
    }
}