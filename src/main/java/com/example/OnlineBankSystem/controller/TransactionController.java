package com.example.OnlineBankSystem.controller;

import com.example.OnlineBankSystem.model.dto.TransactionDto;
import com.example.OnlineBankSystem.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<String> doTransaction(@RequestBody TransactionDto transactionDto) {
        transactionService.executeTransaction(transactionDto);
        return ResponseEntity.ok("Transaction successful");
    }

}
