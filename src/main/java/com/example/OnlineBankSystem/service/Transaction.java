package com.example.OnlineBankSystem.service;

import com.example.OnlineBankSystem.model.Account;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class Transaction {

    protected Account account;

    protected Double amount;

    protected LocalDateTime dateTimeTransaction;

    abstract void execute();
}
