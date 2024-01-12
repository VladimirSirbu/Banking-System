package com.example.OnlineBankSystem.service;


import com.example.OnlineBankSystem.enums.TransactionType;
import lombok.Getter;

@Getter
public class TransactionRequest {

    private TransactionType transactionType;
    private Long accountId;
    private Double amount;

}
