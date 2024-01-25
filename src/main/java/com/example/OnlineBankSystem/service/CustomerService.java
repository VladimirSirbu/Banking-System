package com.example.OnlineBankSystem.service;

import com.example.OnlineBankSystem.exception.CustomerNotFoundException;
import com.example.OnlineBankSystem.model.Customer;
import com.example.OnlineBankSystem.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer) {
       return customerRepository.save(customer);
    }

    public Optional<Customer> getCustomerById(Long id) {
        return Optional.of(customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with ID: " + id + " not found!")));
    }

    public void deleteCustomer(Long id) {
        getCustomerById(id).ifPresent(customer -> customerRepository.deleteById(id));
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
