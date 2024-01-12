package com.example.OnlineBankSystem.model;

import com.example.OnlineBankSystem.enums.AccountState;
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

    @Enumerated(EnumType.STRING)
    private AccountState state;

    public void deposit(double amount) {
        state.deposit(this, amount);
    }

    public void withdraw(double amount) {
        state.withdraw(this, amount);
    }

}
