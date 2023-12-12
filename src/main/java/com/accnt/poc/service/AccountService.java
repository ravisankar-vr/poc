package com.accnt.poc.service;

import com.accnt.poc.entity.Account;
import com.accnt.poc.entity.Transaction;

import java.util.List;

public interface AccountService {
    List<Account> getAccounts(int cust_id);
    Account getAccountById(int accnt_id);
    Account createAccount(Account account);
    Transaction transferMoney(int sourceAccountId, int targetAccountId, Double amount);

}
