package com.example.OnlineBankSystem.model.enums;

import com.example.OnlineBankSystem.service.DepositTransaction;
import com.example.OnlineBankSystem.service.TransactionExecutor;
import com.example.OnlineBankSystem.service.WithdrawalTransaction;
import lombok.Getter;

@Getter
public enum TransactionType {

    WITHDRAW(new WithdrawalTransaction()),
    DEPOSIT(new DepositTransaction());

    private final TransactionExecutor transactionExecutor;

    TransactionType(TransactionExecutor transactionExecutor) {
        this.transactionExecutor = transactionExecutor;
    }
}
