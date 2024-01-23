package com.example.OnlineBankSystem.exception;

public class TransactionExecutionException extends RuntimeException {

    public TransactionExecutionException(String message) {
        super(message);
    }
}