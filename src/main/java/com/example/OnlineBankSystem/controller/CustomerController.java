package com.example.OnlineBankSystem.controller;

import com.example.OnlineBankSystem.model.Customer;
import com.example.OnlineBankSystem.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/new")
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {
        Customer createdAccount = customerService.saveCustomer(customer);
        return ResponseEntity.ok("Customer created with ID: " + createdAccount.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.of(customerService.getCustomerById(id));
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.of(Optional.ofNullable(customerService.getAllCustomers()));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Customer with ID: " + id + " deleted successfully");
    }
}
