package com.example.OnlineBankSystem.model;

public interface State {
    void deposit(Account account, double amount);
    void withdraw(Account account, double amount);
}