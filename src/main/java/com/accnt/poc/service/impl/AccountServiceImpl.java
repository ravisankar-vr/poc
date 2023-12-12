package com.accnt.poc.service.impl;

import com.accnt.poc.entity.Account;
import com.accnt.poc.entity.Transaction;
import com.accnt.poc.enum2.TransactionType;
import com.accnt.poc.exception.InsufficientBalanceException;
import com.accnt.poc.repository.AccountRepository;
import com.accnt.poc.repository.TransactionRepository;
import com.accnt.poc.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public List<Account> getAccounts(int cust_id){
        List<Account>accountList=accountRepository.findByCustId(cust_id);
        if (!accountList.isEmpty()) {

            List<Transaction> transactionList=transactionRepository.findByCustId(cust_id);
            if (!transactionList.isEmpty()) {

                accountList = updateAccounts(accountList, transactionList);
               /* accountList.stream()
                        .map(acc ->  {
                            acc.setTransactions(transactionList);
                            return acc;
                        }
                        ).collect(Collectors.toList());*/
            }
            return accountList;
        } else {
           return null;
        }
    }

    public Account getAccountById(int accnt_id){
        Optional<Account> account=accountRepository.findById(accnt_id);
        if (account.isPresent()) {
            return account.get();
        } else {
            return null;
        }
    }

    @Override
    public Account createAccount(Account account){
        Account response=accountRepository.save(account);
        return response;
    }

    @Transactional
    @Override
    public Transaction transferMoney(int sourceAccountId, int targetAccountId, Double amount) {
        Account sourceAccount=getAccountById(sourceAccountId);
        Account targetAccount=getAccountById(targetAccountId);

        // Check if the source account has sufficient balance
        if (sourceAccount.getAccBal().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance in the source account.");
        }

        // Update account balances
        sourceAccount.setAccBal(sourceAccount.getAccBal() - amount);
        targetAccount.setAccBal(targetAccount.getAccBal() + amount);

        // Save the updated accounts
        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);

        // Record the transaction
        Transaction transaction = new Transaction();
        transaction.setSourceAccountId(""+sourceAccountId);
        transaction.setCustId(sourceAccount.getCust_Id());
        transaction.setAccId(sourceAccountId);
        transaction.setTargetAccountId(""+targetAccountId);
        transaction.setAmount(amount);
        transaction.setTransaction_type(TransactionType.TRANSFER);
        transaction.setTransaction_date(LocalDateTime.now());
        // Save to Transaction
        transactionRepository.save(transaction);

        return transaction;
    }



    public static List<Account> updateAccounts(List<Account> accounts, List<Transaction> transactions) {
        // Step 1: Create a mapping of account IDs to accounts
        Map<Integer, Account> accountMap = accounts.stream()
                .collect(Collectors.toMap(Account::getId, account -> account));

        // Step 2: Filter transactions based on account IDs in the first list
        List<Transaction> filteredTransactions = transactions.stream()
                .filter(transaction -> accountMap.containsKey(transaction.getAccId()))
                .collect(Collectors.toList());

        // Step 3: Group filtered transactions by account ID
        Map<Integer, List<Transaction>> transactionsByAccountId = filteredTransactions.stream()
                .collect(Collectors.groupingBy(Transaction::getAccId));

        // Step 4: Update accounts with corresponding transactions
        accounts.forEach(account -> {
            int accountId = account.getId();
            if (transactionsByAccountId.containsKey(accountId)) {
                List<Transaction> accountTransactions = transactionsByAccountId.get(accountId);
                account.setTransactions(accountTransactions);
            }
        });

        return accounts;
    }

    // Define your Account and Transaction classes with appropriate methods




}
