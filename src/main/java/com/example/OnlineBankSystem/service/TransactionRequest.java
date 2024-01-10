package com.example.OnlineBankSystem.service;


import lombok.Getter;

@Getter
public class TransactionRequest {

    private TransactionType transactionType;
    private Long accountId;
    private Double amount;

}
