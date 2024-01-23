package com.example.OnlineBankSystem.model.dto;

import com.example.OnlineBankSystem.model.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionDto {

    private TransactionType transactionType;
    private Double amount;
    private LocalDateTime createdAt;
    private Long accountId;
}
