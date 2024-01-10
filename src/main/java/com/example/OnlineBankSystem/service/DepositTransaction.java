package com.example.OnlineBankSystem.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DepositTransaction extends Transaction {

    @Override
    public void execute() {
        account.deposit(amount);
        dateTimeTransaction = LocalDateTime.now();
    }
}
