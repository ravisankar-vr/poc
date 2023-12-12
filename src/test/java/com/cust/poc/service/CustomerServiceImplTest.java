package com.cust.poc.service;

import com.cust.poc.entity.Account;
import com.cust.poc.entity.Customer;
import com.cust.poc.entity.Transaction;
import com.cust.poc.enum2.AccountStatus;
import com.cust.poc.enum2.AccountType;
import com.cust.poc.enum2.TransactionType;
import com.cust.poc.feign.AccountFeignClient;
import com.cust.poc.repository.CustomerRepository;
import com.cust.poc.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    AccountFeignClient accountFeignClient;

    @InjectMocks
    CustomerService customerService=new CustomerServiceImpl();

    @Test @Order(1)
    public void test_getCustomers(){

        List<Transaction> transactionList=Arrays.asList(
                new Transaction("1","2",200.0, TransactionType.TRANSFER, LocalDateTime.now())
        );

        List<Account> accountList =Arrays.asList(
                new Account(1,"AC005",5000,transactionList),
                new Account(2,"AC005",4000,transactionList)
        );

        List<Customer> customerList= Arrays.asList(
                new Customer(1,"Ramesh", AccountType.SAVING_ACCOUNT, AccountStatus.ACTIVE,"9879769855","Ramesh@gmail.com",accountList)
        );

        when(customerRepository.findAll()).thenReturn(customerList);
        assertEquals(1, customerService.getCustomers().size());
    }

    @Test @Order(2)
    public void test_getCustomerById(){
        int custId=1;

        List<Transaction> transactionList=Arrays.asList(
                new Transaction("1","2",200.0, TransactionType.TRANSFER, LocalDateTime.now())
        );

        List<Account> accountList =Arrays.asList(
                new Account(1,"AC005",5000,transactionList),
                new Account(2,"AC005",4000,transactionList)
        );

        Customer customer=new Customer(1,"Ramesh", AccountType.SAVING_ACCOUNT, AccountStatus.ACTIVE,"9879769855","Ramesh@gmail.com",accountList);
        when(customerRepository.findById(custId)).thenReturn(Optional.ofNullable(customer));
        when(accountFeignClient.getAccountDetails(custId)).thenReturn(accountList);
        assertTrue(customerService.getCustomerById(custId).isPresent());

    }

    @Test @Order(3)
    public void test_createCustomer(){
        Customer customer=new Customer("Raj", AccountType.SAVING_ACCOUNT, AccountStatus.ACTIVE,"9879769666","Raj@gmail.com");
        when(customerRepository.save(customer)).thenReturn(customer);
        assertEquals(customer, customerService.createCustomer(customer));
    }

}
