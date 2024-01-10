package com.example.OnlineBankSystem.controller;

import com.example.OnlineBankSystem.service.TransactionRequest;
import com.example.OnlineBankSystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<String> doTransaction(@RequestBody TransactionRequest transactionRequest) {
        transactionService.executeTransaction(transactionRequest);
        return ResponseEntity.ok("Transaction successful");
    }


}
