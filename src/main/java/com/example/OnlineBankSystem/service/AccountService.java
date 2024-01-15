package com.example.OnlineBankSystem.service;

import com.example.OnlineBankSystem.exception.AccountNotFoundException;
import com.example.OnlineBankSystem.model.Account;
import com.example.OnlineBankSystem.repository.AccountRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import static com.example.OnlineBankSystem.service.utilis.OnlineBankSystemConstants.MY_SQL_CIRCUIT_BREAKER;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private static AtomicLong requestNumber = new AtomicLong(0);

    public Account createAccount(Account account) {
        // You can add additional validation or business logic here
        return accountRepository.save(account);
    }

    public Optional<Account> getAccountById(Long id) {
        return Optional.of(accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account with ID: " + id + " not found!")));
    }

    @CircuitBreaker(name = MY_SQL_CIRCUIT_BREAKER)
    public Set<Account> getAllAccounts() {
        Set<Account> accounts = accountRepository.findAllAccounts();
        log.info("Extracted " + accounts.size() + " accounts from Data Base ( Request: " + requestNumber.incrementAndGet() + ")");
        return accounts;
    }

    public void deleteAccount(Long id) {
        getAccountById(id).ifPresent(account -> accountRepository.deleteById(id));
    }
}