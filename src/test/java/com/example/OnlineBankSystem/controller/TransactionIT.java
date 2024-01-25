package com.example.OnlineBankSystem.controller;

import com.example.OnlineBankSystem.exception.AccountInactiveException;
import com.example.OnlineBankSystem.exception.TransactionExecutionException;
import com.example.OnlineBankSystem.model.dto.TransactionDto;
import com.example.OnlineBankSystem.model.enums.TransactionType;
import com.example.OnlineBankSystem.repository.AccountRepository;
import com.example.OnlineBankSystem.service.TransactionService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class TransactionIT {

    @LocalServerPort
    private  int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;

    private String url;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    @BeforeAll
    void beforeAll() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        url = "http://localhost:" + port + "/bank";
    }

    @Test
    void doTransactionDepositTest() {

        String depositTransaction = "{\n" +
                "   \"transactionType\": \"DEPOSIT\",\n" +
                "   \"amount\": 120.0,\n" +
                "   \"accountId\": 102 \n" +
                "}";

        Double balanceBeforeTransaction = accountRepository.findById(102L).get().getBalance();

        HttpEntity<String> httpEntity = new HttpEntity<>(depositTransaction, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url + "/transactions",
                HttpMethod.POST,
                httpEntity,
                String.class);

        Double balanceAfterTransaction = accountRepository.findById(102L).get().getBalance();

        assertEquals(balanceBeforeTransaction + 120.0, balanceAfterTransaction);
    }

    @Test
    void doTransactionWithdrawTest() {

        String withdrawTransaction = "{\n" +
                "   \"transactionType\": \"WITHDRAW\",\n" +
                "   \"amount\": 500.0,\n" +
                "   \"accountId\": 101 \n" +
                "}";

        Double balanceBeforeTransaction = accountRepository.findById(101L).get().getBalance();

        HttpEntity<String> httpEntity = new HttpEntity<>(withdrawTransaction, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url + "/transactions",
                HttpMethod.POST,
                httpEntity,
                String.class);

        Double balanceAfterTransaction = accountRepository.findById(101L).get().getBalance();

        assertEquals(balanceBeforeTransaction - 500.0, balanceAfterTransaction);
    }

    @Test
    void doTransactionWithdrawAndExpectAccountInactiveException() {
        // account with ID: 105 is INACTIVE
        TransactionDto transactionDto = TransactionDto.builder()
                .transactionType(TransactionType.WITHDRAW)
                .amount(100.0)
                .accountId(105L)
                .build();

        assertThrows(AccountInactiveException.class, () -> transactionService.executeTransaction(transactionDto));
    }

    @Test
    void doTransactionWithdrawAndExpectTransactionExecutionException() {
        // in account with ID: 103 there is balance = 60.0 < 100.0
        TransactionDto transactionDto = TransactionDto.builder()
                .transactionType(TransactionType.WITHDRAW)
                .amount(100.0)
                .accountId(103L)
                .build();

        assertThrows(TransactionExecutionException.class, () -> transactionService.executeTransaction(transactionDto));
    }

}
