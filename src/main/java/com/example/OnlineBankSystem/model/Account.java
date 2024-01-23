package com.example.OnlineBankSystem.model;

import com.example.OnlineBankSystem.exception.TransactionExecutionException;
import com.example.OnlineBankSystem.model.enums.AccountState;
import com.example.OnlineBankSystem.model.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", unique = true)
    private String accountNumber;

    @Column(name = "balance")
    private Double balance; //TODO: on creating, make balance = 0

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private AccountState state;

    @Column(name = "account_type")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @JsonManagedReference
    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions; //TODO: delete all FetchType.LAZY

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= this.balance)
            this.balance -= amount;
        else
            throw new TransactionExecutionException("Transaction Exception! Amount " + amount + " exceeds the limit of your balance");
    }

}
