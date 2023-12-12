package com.accnt.poc.controller;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import com.accnt.poc.entity.Account;
import com.accnt.poc.entity.Transaction;
import com.accnt.poc.enum2.TransactionType;
import com.accnt.poc.exception.RecordNotFoundException;
import com.accnt.poc.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.ArrayList;

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

@ContextConfiguration(classes = {AccountController.class})
@ExtendWith(SpringExtension.class)
class AccountControllerTest {
    @Autowired
    private AccountController accountController;

    @MockBean
    private AccountService accountService;

    /**
     * Method under test: {@link AccountController#createAccount(Account)}
     */
    @Test
    void testCreateAccount() throws Exception {
        Account account = new Account();
        account.setAccBal(10.0d);
        account.setAccNo("Acc No");
        account.setCust_Id(1);
        account.setId(1);
        account.setTransactions(new ArrayList<>());
        when(accountService.createAccount(Mockito.<Account>any())).thenReturn(account);

        Account account2 = new Account();
        account2.setAccBal(10.0d);
        account2.setAccNo("Acc No");
        account2.setCust_Id(1);
        account2.setId(1);
        account2.setTransactions(new ArrayList<>());
        String content = (new ObjectMapper()).writeValueAsString(account2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/account/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(accountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"msg\":\"Account saved successfully\"}"));
    }

    /**
     * Method under test: {@link AccountController#createAccount(Account)}
     */
    @Test
    void testCreateAccount2() throws Exception {
        when(accountService.createAccount(Mockito.<Account>any()))
                .thenThrow(new RecordNotFoundException("An error occurred"));

        Account account = new Account();
        account.setAccBal(10.0d);
        account.setAccNo("Acc No");
        account.setCust_Id(1);
        account.setId(1);
        account.setTransactions(new ArrayList<>());
        String content = (new ObjectMapper()).writeValueAsString(account);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/account/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(accountController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link AccountController#transferMoney(int, int, Double)}
     */
    @Test
    void testTransferMoney() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setAccId(1);
        transaction.setAmount(10.0d);
        transaction.setCustId(1);
        transaction.setId(1);
        transaction.setSourceAccountId("42");
        transaction.setTargetAccountId("42");
        transaction.setTransaction_date(LocalDate.of(1970, 1, 1).atStartOfDay());
        transaction.setTransaction_type(TransactionType.PAYMENT);
        when(accountService.transferMoney(anyInt(), anyInt(), Mockito.<Double>any())).thenReturn(transaction);
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/api/account/transfer");
        MockHttpServletRequestBuilder paramResult = postResult.param("amount", String.valueOf(10.0d));
        MockHttpServletRequestBuilder paramResult2 = paramResult.param("sourceAccountId", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult2.param("targetAccountId", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(accountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"data\":{\"id\":1,\"source_account_id\":\"42\",\"target_account_id\":\"42\",\"amount\":10.0,\"transaction_type"
                                        + "\":\"PAYMENT\",\"transaction_date\":[1970,1,1,0,0]},\"message\":\"Transfered amount successfully\",\"status"
                                        + "\":\"OK\"}"));
    }

    /**
     * Method under test: {@link AccountController#transferMoney(int, int, Double)}
     */
    @Test
    void testTransferMoney2() throws Exception {
        when(accountService.transferMoney(anyInt(), anyInt(), Mockito.<Double>any()))
                .thenThrow(new RecordNotFoundException("An error occurred"));
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/api/account/transfer");
        MockHttpServletRequestBuilder paramResult = postResult.param("amount", String.valueOf(10.0d));
        MockHttpServletRequestBuilder paramResult2 = paramResult.param("sourceAccountId", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult2.param("targetAccountId", String.valueOf(1));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(accountController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link AccountController#getAccountDetails(int)}
     */
    @Test
    void testGetAccountDetails() throws Exception {
        when(accountService.getAccounts(anyInt())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/account/{custId}", 1);
        MockMvcBuilders.standaloneSetup(accountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link AccountController#getAccountDetails(int)}
     */
    @Test
    void testGetAccountDetails2() throws Exception {
        when(accountService.getAccounts(anyInt())).thenThrow(new RecordNotFoundException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/account/{custId}", 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(accountController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
