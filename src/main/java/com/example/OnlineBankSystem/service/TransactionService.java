package com.example.OnlineBankSystem.service;

import com.example.OnlineBankSystem.exception.AccountInactiveException;
import com.example.OnlineBankSystem.model.Transaction;
import com.example.OnlineBankSystem.model.dto.TransactionDto;
import com.example.OnlineBankSystem.model.enums.AccountState;
import com.example.OnlineBankSystem.repository.TransactionRepository;
import com.example.OnlineBankSystem.service.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionMapper transactionMapper;

    private final TransactionRepository transactionRepository;

    @Transactional
    public void executeTransaction(TransactionDto transactionDto) {

        Transaction transaction = transactionMapper.transactionDtoToTransaction(transactionDto);

        if (transaction.getAccount().getState().equals(AccountState.INACTIVE))
            throw new AccountInactiveException("You can not execute any transaction because your account is INACTIVE");

        var transactionExecutor = transaction.getTransactionType().getTransactionExecutor();
        transactionExecutor.execute(transaction.getAccount(), transaction.getAmount());
        transaction.setCreatedAt(LocalDateTime.now());
        transactionRepository.save(transaction);
    }
}
