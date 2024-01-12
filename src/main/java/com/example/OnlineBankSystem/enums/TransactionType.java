package com.example.OnlineBankSystem.enums;

import com.example.OnlineBankSystem.service.DepositTransaction;
import com.example.OnlineBankSystem.service.Transaction;
import com.example.OnlineBankSystem.service.WithdrawalTransaction;
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
