package com.example.OnlineBankSystem.service;

import com.example.OnlineBankSystem.model.Account;

public interface TransactionExecutor {
     void execute(Account account, Double amount);
}
