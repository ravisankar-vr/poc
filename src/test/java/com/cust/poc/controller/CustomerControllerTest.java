package com.cust.poc.controller;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import com.cust.poc.entity.Customer;
import com.cust.poc.enum2.AccountStatus;
import com.cust.poc.enum2.AccountType;
import com.cust.poc.exception.RecordNotFoundException;
import com.cust.poc.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CustomerController.class})
@ExtendWith(SpringExtension.class)
class CustomerControllerTest {
    @Autowired
    private CustomerController customerController;

    @MockBean
    private CustomerService customerService;

    /**
     * Method under test: {@link CustomerController#getCustomerById(int)}
     */
    @Test
    void testGetCustomerById() throws Exception {
        Customer customer = new Customer();
        customer.setAccounts(new ArrayList<>());
        customer.setCustEmail("jane.doe@example.org");
        customer.setCustName("Cust Name");
        customer.setCustPhone("6625550144");
        customer.setId(1);
        customer.setStatus(AccountStatus.ACTIVE);
        customer.setType(AccountType.SAVING_ACCOUNT);
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerService.getCustomerById(anyInt())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/customer/{custId}", 1);
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"data\":{\"id\":1,\"name\":\"Cust Name\",\"acc_type\":\"SAVING_ACCOUNT\",\"acc_status\":\"ACTIVE\",\"phone\":\"6625550144"
                                        + "\",\"email\":\"jane.doe@example.org\",\"account\":[]},\"message\":\"Fetch Customer Details by ID : 1\",\"status\""
                                        + ":\"OK\"}"));
    }

    /**
     * Method under test: {@link CustomerController#getCustomerById(int)}
     */
    @Test
    void testGetCustomerById2() throws Exception {
        Optional<Customer> emptyResult = Optional.empty();
        when(customerService.getCustomerById(anyInt())).thenReturn(emptyResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/customer/{custId}", 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link CustomerController#getCustomerById(int)}
     */
    @Test
    void testGetCustomerById3() throws Exception {
        when(customerService.getCustomerById(anyInt())).thenThrow(new RecordNotFoundException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/customer/{custId}", 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link CustomerController#createCustomer(Customer)}
     */
    @Test
    void testCreateCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setAccounts(new ArrayList<>());
        customer.setCustEmail("jane.doe@example.org");
        customer.setCustName("Cust Name");
        customer.setCustPhone("6625550144");
        customer.setId(1);
        customer.setStatus(AccountStatus.ACTIVE);
        customer.setType(AccountType.SAVING_ACCOUNT);
        when(customerService.createCustomer(Mockito.<Customer>any())).thenReturn(customer);

        Customer customer2 = new Customer();
        customer2.setAccounts(new ArrayList<>());
        customer2.setCustEmail("jane.doe@example.org");
        customer2.setCustName("Cust Name");
        customer2.setCustPhone("6625550144");
        customer2.setId(1);
        customer2.setStatus(AccountStatus.ACTIVE);
        customer2.setType(AccountType.SAVING_ACCOUNT);
        String content = (new ObjectMapper()).writeValueAsString(customer2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/customer/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"data\":{\"id\":1,\"name\":\"Cust Name\",\"acc_type\":\"SAVING_ACCOUNT\",\"acc_status\":\"ACTIVE\",\"phone\":\"6625550144"
                                        + "\",\"email\":\"jane.doe@example.org\",\"account\":[]},\"message\":\"Save Customer Details\",\"status\":\"OK\"}"));
    }

    /**
     * Method under test: {@link CustomerController#createCustomer(Customer)}
     */
    @Test
    void testCreateCustomer2() throws Exception {
        when(customerService.createCustomer(Mockito.<Customer>any()))
                .thenThrow(new RecordNotFoundException("An error occurred"));

        Customer customer = new Customer();
        customer.setAccounts(new ArrayList<>());
        customer.setCustEmail("jane.doe@example.org");
        customer.setCustName("Cust Name");
        customer.setCustPhone("6625550144");
        customer.setId(1);
        customer.setStatus(AccountStatus.ACTIVE);
        customer.setType(AccountType.SAVING_ACCOUNT);
        String content = (new ObjectMapper()).writeValueAsString(customer);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/customer/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link CustomerController#getCustomerDetails()}
     */
    @Test
    void testGetCustomerDetails() throws Exception {
        when(customerService.getCustomers()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/customer/");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link CustomerController#getCustomerDetails()}
     */
    @Test
    void testGetCustomerDetails2() throws Exception {
        Customer customer = new Customer();
        customer.setAccounts(new ArrayList<>());
        customer.setCustEmail("jane.doe@example.org");
        customer.setCustName("In CustomerService RestController - to fetch All Customer Details");
        customer.setCustPhone("6625550144");
        customer.setId(1);
        customer.setStatus(AccountStatus.ACTIVE);
        customer.setType(AccountType.SAVING_ACCOUNT);

        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        when(customerService.getCustomers()).thenReturn(customerList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/customer/");
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"data\":[{\"id\":1,\"name\":\"In CustomerService RestController - to fetch All Customer Details\",\"acc_type"
                                        + "\":\"SAVING_ACCOUNT\",\"acc_status\":\"ACTIVE\",\"phone\":\"6625550144\",\"email\":\"jane.doe@example.org\",\"account"
                                        + "\":[]}],\"message\":\"List of all Customers\",\"status\":\"OK\"}"));
    }

    /**
     * Method under test: {@link CustomerController#getCustomerDetails()}
     */
    @Test
    void testGetCustomerDetails3() throws Exception {
        when(customerService.getCustomers()).thenThrow(new RecordNotFoundException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/customer/");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
