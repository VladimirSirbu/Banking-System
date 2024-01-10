package com.example.OnlineBankSystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountService accountService;

    @Transactional
    public void executeTransaction(TransactionRequest transactionRequest) {

        var transaction = transactionRequest.getTransactionType().getTransaction();

        var account = accountService.getAccountById(transactionRequest.getAccountId()).get();

        transaction.setAccount(account);
        transaction.setAmount(transactionRequest.getAmount());

        transaction.execute();
    }
}
