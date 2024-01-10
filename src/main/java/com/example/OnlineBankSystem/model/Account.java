package com.example.OnlineBankSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String accountNumber;

    private Double balance;

    public void deposit(double amount) {
        this.balance += amount;
        System.out.println("Deposited $" + amount + " to account " + accountNumber + ". New balance: $" + balance); // TODO: Implement logging
    }

    public void withdraw(double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            System.out.println("Withdrawn $" + amount + " from account " + accountNumber + ". New balance: $" + balance);
        } else {
            System.out.println("Insufficient funds for withdrawal from account " + accountNumber);
        }
    }
}
