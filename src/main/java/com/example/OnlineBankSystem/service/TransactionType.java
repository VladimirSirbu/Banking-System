package com.example.OnlineBankSystem.service;

import lombok.Getter;

@Getter
public enum TransactionType {
    WITHDRAW(new WithdrawalTransaction()),
    DEPOSIT(new DepositTransaction());

    private final Transaction transaction;

    TransactionType(Transaction transaction) {
        this.transaction = transaction;
    }

}
