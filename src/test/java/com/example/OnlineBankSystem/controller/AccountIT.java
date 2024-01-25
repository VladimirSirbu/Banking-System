package com.example.OnlineBankSystem.controller;

import com.example.OnlineBankSystem.model.Account;
import com.example.OnlineBankSystem.repository.AccountRepository;
import com.example.OnlineBankSystem.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class AccountIT {

    @LocalServerPort
    private  int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;

    private String url;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeAll
    void beforeAll() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        url = "http://localhost:" + port + "/bank";
    }

    @Test
    void createAccountTest() {
        String newAccount = "{\n" +
                "   \"accountNumber\": \"RO54RZBR8432223381441394\",\n" +
                "   \"balance\": 1200.0,\n" +
                "   \"state\": \"ACTIVE\",\n" +
                "   \"accountType\": \"CHECKING\",\n" +
                "   \"customer\": {\n" +
                "      \"firstName\": \"Jennifer\",\n" +
                "      \"lastName\": \"Hall\",\n" +
                "      \"email\": \"jennifer.hall@gmail.com\",\n" +
                "      \"address\": {\n" +
                "         \"street\": \"234 Maple Street\",\n" +
                "         \"city\": \"New York\",\n" +
                "         \"postalCode\": \"92831\",\n" +
                "         \"country\": \"USA\"\n" +
                "      }\n" +
                "   }\n" +
                "}";

        int numberOfAccountsInDBBeforeSave = accountRepository.findAll().size();

        HttpEntity<String> httpEntity = new HttpEntity<>(newAccount, headers);

        ResponseEntity<String> response = restTemplate
                .postForEntity(url + "/accounts/new", httpEntity, String.class);

        int numberOfAccountsInDBAfterSave = accountRepository.findAll().size();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(numberOfAccountsInDBBeforeSave + 1, numberOfAccountsInDBAfterSave);
    }

    @Test
    void getAccountByIdTest() {
        ResponseEntity<Account> response = restTemplate.getForEntity(url + "/accounts/101", Account.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("RO53RZBR9323762774847299", Objects.requireNonNull(response.getBody()).getAccountNumber());
    }

    @Test
    void getAllAccountsTest() {
        ResponseEntity<List<Account>> response = restTemplate.exchange(
                url + "/accounts",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Account>>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(Objects.requireNonNull(response.getBody()).size()).isNotZero();
    }

    @Test
    void deleteAccountTest() {
        int numberOfCustomerInDBBeforeDelete = accountRepository.findAll().size();

        ResponseEntity<String> response = restTemplate.exchange(
                url + "/accounts/remove/105",
                HttpMethod.DELETE,
                null,
                String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        int numberOfCustomerInDBAfterDelete = accountRepository.findAll().size();
        assertEquals(numberOfCustomerInDBBeforeDelete - 1, numberOfCustomerInDBAfterDelete);
    }

    @Test
    void deleteAccountExpectNotFoundAssociateCustomer() {
        // if I delete an account, its customer also will be deleted
        ResponseEntity<String> response = restTemplate.exchange(
                url + "/accounts/remove/104",
                HttpMethod.DELETE,
                null,
                String.class);

        assertThrows(NoSuchElementException.class, () -> {
            customerRepository.findById(4L).get();
        });
    }

}
