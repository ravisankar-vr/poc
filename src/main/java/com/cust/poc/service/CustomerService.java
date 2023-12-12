package com.cust.poc.service;


import com.cust.poc.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customer> getCustomers();
    Optional<Customer> getCustomerById(int custId);
    Customer createCustomer(Customer customer);
}
