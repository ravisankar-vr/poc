package com.cust.poc.service.impl;

import com.cust.poc.entity.Account;
import com.cust.poc.entity.Customer;
import com.cust.poc.feign.AccountFeignClient;
import com.cust.poc.repository.CustomerRepository;
import com.cust.poc.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountFeignClient accountFeignClient;

    @Override
    public List<Customer> getCustomers() {
        List<Customer> customerList = customerRepository.findAll();
        if (!customerList.isEmpty()) {

            customerList.forEach(customer -> {
                int customerId = customer.getId();
                List<Account> accountList = accountFeignClient.getAccountDetails(customerId);
                customer.setAccounts(accountList);
            });

            return customerList;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Customer> getCustomerById(int custId) {
        Optional<Customer> customer=customerRepository.findById(custId);
        if (customer.isPresent()) {

            List<Account> accountList = accountFeignClient.getAccountDetails(custId);
            customer.get().setAccounts(accountList);

            return customer;
        } else {
            return null;
        }
    }

    @Override
    public Customer createCustomer(Customer customer){
        return customerRepository.save(customer);
    }
}
