package com.example.OnlineBankSystem.controller;

import com.example.OnlineBankSystem.model.Account;
import com.example.OnlineBankSystem.model.Customer;
import com.example.OnlineBankSystem.model.enums.AccountState;
import com.example.OnlineBankSystem.model.enums.AccountType;
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
public class CustomerIT {

    @LocalServerPort
    private  int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;

    private String url;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeAll
    void beforeAll() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        url = "http://localhost:" + port + "/bank";
    }

    @Test
    void createCustomerTest() {
        String john = "   {\n" +
                "      \"firstName\": \"Olivia\",\n" +
                "      \"lastName\": \"Anderson\",\n" +
                "      \"email\": \"olivia.anderson@gmail.com\",\n" +
                "      \"address\": {\n" +
                "         \"street\": \"14 Trandafirilor Street\",\n" +
                "         \"city\": \"Bucuresti\",\n" +
                "         \"postalCode\": \"602134\",\n" +
                "         \"country\": \"RO\"\n" +
                "      },\n" +
                "      \"accounts\": []\n" +
                "   }";

        int numberOfCustomerInDBBeforeSave = customerRepository.findAll().size();

        HttpEntity<String> httpEntity = new HttpEntity<>(john, headers);

        ResponseEntity<String> response = restTemplate
                .postForEntity(url + "/customers/new", httpEntity, String.class);

        int numberOfCustomerInDBAfterSave = customerRepository.findAll().size();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(numberOfCustomerInDBBeforeSave + 1, numberOfCustomerInDBAfterSave);
    }

    @Test
    void getCustomerByIdTest() {
        ResponseEntity<Customer> response = restTemplate.getForEntity(url + "/customers/1", Customer.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John", Objects.requireNonNull(response.getBody()).getFirstName());
    }

    @Test
    void getAllCustomersTest() {
        ResponseEntity<List<Customer>> response = restTemplate.exchange(
                url + "/customers",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Customer>>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(Objects.requireNonNull(response.getBody()).size()).isNotZero();
    }

    @Test
    void deleteCustomerTest() {

        int numberOfCustomerInDBBeforeDelete = customerRepository.findAll().size();

        ResponseEntity<String> response = restTemplate.exchange(
                url + "/customers/remove/5",
                HttpMethod.DELETE,
                null,
                String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        int numberOfCustomerInDBAfterDelete = customerRepository.findAll().size();
        assertEquals(numberOfCustomerInDBBeforeDelete - 1, numberOfCustomerInDBAfterDelete);
    }

    @Test
    void deleteCustomerExpectNotFoundAssociateAccount() {
        // if I delete a customer, his account also will be deleted
        ResponseEntity<String> response = restTemplate.exchange(
                url + "/customers/remove/4",
                HttpMethod.DELETE,
                null,
                String.class);

        assertThrows(NoSuchElementException.class, () -> {
            accountRepository.findById(104L).get();
        });
    }

    @Test
    void saveCustomerWithManyAccounts() {
        Account checkingAccount = Account.builder()
                .accountNumber("RO122353456")
                .balance(200.0)
                .state(AccountState.ACTIVE)
                .accountType(AccountType.CHECKING)
                .build();

        Account savingAccount = Account.builder()
                .accountNumber("RO122353451")
                .balance(600.0)
                .state(AccountState.ACTIVE)
                .accountType(AccountType.SAVING)
                .build();

        Customer customer = Customer.builder()
                .firstName("Johnny")
                .lastName("Deep")
                .email("johnny.deep@gmail.com")
                .accounts(List.of(checkingAccount, savingAccount))
                .build();

        savingAccount.setCustomer(customer);
        checkingAccount.setCustomer(customer);

        Customer savedCustomer = customerRepository.save(customer);

        assertThat(savedCustomer).isNotNull();
        assertEquals(2, savedCustomer.getAccounts().size());
    }

}
