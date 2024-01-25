package com.example.OnlineBankSystem.model.dto;

import com.example.OnlineBankSystem.model.enums.TransactionType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class TransactionDto {

    private TransactionType transactionType;
    private Double amount;
    private LocalDateTime createdAt;
    private Long accountId;
}
