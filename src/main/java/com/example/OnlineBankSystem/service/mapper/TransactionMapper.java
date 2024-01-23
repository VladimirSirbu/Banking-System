package com.example.OnlineBankSystem.service.mapper;

import com.example.OnlineBankSystem.exception.AccountNotFoundException;
import com.example.OnlineBankSystem.model.Account;
import com.example.OnlineBankSystem.model.Transaction;
import com.example.OnlineBankSystem.model.dto.TransactionDto;
import com.example.OnlineBankSystem.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionMapper {

    private final AccountRepository accountRepository;

    public Transaction transactionDtoToTransaction(TransactionDto transactionDto) {
        if (transactionDto == null)
            return null;
        Transaction transaction = new Transaction();

        transaction.setTransactionType(transactionDto.getTransactionType());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setCreatedAt(transactionDto.getCreatedAt());

        accountRepository.findById(transactionDto.getAccountId())
                .ifPresentOrElse(
                        transaction::setAccount,
                        () -> {throw new AccountNotFoundException("During mapping account not found by id.");}
        );

        return transaction;
    }
}
