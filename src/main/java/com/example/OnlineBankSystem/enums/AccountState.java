package com.example.OnlineBankSystem.enums;

import com.example.OnlineBankSystem.exception.AccountInactiveException;
import com.example.OnlineBankSystem.model.Account;
import com.example.OnlineBankSystem.model.State;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum AccountState implements State {
    ACTIVE {
        @Override
        public void deposit(Account account, double amount) {
            account.setBalance(account.getBalance() + amount);
            log.info("Deposited " + amount + "$ in account " + account.getAccountNumber() + ". Current balance: " + account.getBalance() + "$");
        }

        @Override
        public void withdraw(Account account, double amount) {
            if (amount <= account.getBalance()) {
                account.setBalance(account.getBalance() - amount);
                log.info("Withdrawn " + amount + "$ from account " + account.getAccountNumber() + ". Current balance: " + account.getBalance() + "$");
            } else {
                log.info("Can't process withdraw. Amount: " + amount + " exceeds the account balance");
            }
        }
    },
    INACTIVE {
        @Override
        public void deposit(Account account, double amount) {
            log.info("Unable to deposit because account "  + account.getAccountNumber() + " is INACTIVE!");
            throw new AccountInactiveException("Account " + account.getAccountNumber() + " is INACTIVE.");
        }

        @Override
        public void withdraw(Account account, double amount) {
            log.info("Unable to withdraw because account "  + account.getAccountNumber() + " is INACTIVE!");
            throw new AccountInactiveException("Account " + account.getAccountNumber() + " is INACTIVE.");
        }
    };

}