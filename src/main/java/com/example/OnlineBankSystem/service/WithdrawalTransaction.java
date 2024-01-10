package com.example.OnlineBankSystem.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WithdrawalTransaction extends Transaction {

    @Override
    public void execute() {
        account.withdraw(amount);
        dateTimeTransaction = LocalDateTime.now();
    }
}
