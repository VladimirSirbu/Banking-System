package com.example.OnlineBankSystem.service;

import com.example.OnlineBankSystem.model.Account;
import org.springframework.stereotype.Service;

@Service
public class WithdrawalTransaction implements TransactionExecutor {

    @Override
    public void execute(Account account, Double amount) {
        account.withdraw(amount);
    }
}
