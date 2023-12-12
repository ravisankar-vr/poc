package com.accnt.poc.service;

import com.accnt.poc.entity.Account;
import com.accnt.poc.entity.Transaction;
import com.accnt.poc.enum2.TransactionType;
import com.accnt.poc.repository.AccountRepository;
import com.accnt.poc.repository.TransactionRepository;
import com.accnt.poc.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AccountServiceImplTest {

    @Mock
    AccountRepository accountRepository;

    @Mock
    TransactionRepository transactionRepository;

    @InjectMocks
    AccountService accountService=new AccountServiceImpl();

    @Test @Order(1)
    public void test_getAccounts(){

        int custId=3;

        List<Transaction> transactionList_1=Arrays.asList(
                new Transaction("5","6",2000.0, TransactionType.TRANSFER, LocalDateTime.now(), 3)
        );
       /* List<Transaction> transactionList_2=Arrays.asList(
                new Transaction("3","4",1000.0, TransactionType.TRANSFER, LocalDateTime.now(), 3)
        );*/

        List<Account> accountList = Arrays.asList(
                new Account(1,3,"AC007",6000.0,transactionList_1),
                new Account(2, 3,"AC008",4000.0,transactionList_1)
        );

        when(accountRepository.findByCustId(custId)).thenReturn(accountList);
        when(transactionRepository.findByCustId(custId)).thenReturn(transactionList_1);
        assertEquals(2, accountService.getAccounts(custId).size());

    }

    @Test @Order(2)
    public void test_getAccountById(){

        int accnt_id=2;
        List<Transaction> transactionList_1=Arrays.asList(
                new Transaction("5","6",2000.0, TransactionType.TRANSFER, LocalDateTime.now(), 3)
        );

        List<Account> accountList = Arrays.asList(
                new Account(1,3,"AC007",6000.0,transactionList_1),
                new Account(2, 3,"AC008",4000.0,transactionList_1)
        );

        Optional<Account> result = convertListToOptional(accountList);

        when(accountRepository.findById(accnt_id)).thenReturn(result);
        assertEquals(1,accountService.getAccountById(accnt_id).getId());

    }

    @Test @Order(3)
    public void test_createAccount(){
        List<Transaction> transactionList_1=Arrays.asList(
                new Transaction("2","3",2000.0, TransactionType.TRANSFER, LocalDateTime.now(), 4)
        );
        Account account=new Account(5, 4, "AC005", 5000.0, transactionList_1);
        when(accountRepository.save(account)).thenReturn(account);
        assertEquals(account, accountService.createAccount(account));

    }

    /**
     * This method checks the size of the list and returns an Optional accordingly.
     * If the list is empty, it returns an empty Optional.
     * If the list has one element, it returns an Optional containing that element
     * If the list has more than one element, it returns all elements
     * @param list
     * @return
     * @param <T>
     */
    public static <T> Optional<T> convertListToOptional(List<T> list) {
        if (list.isEmpty()) {
            return Optional.empty();
        } else if (list.size() == 1) {
            return Optional.of(list.get(0));
        } else {
            // If there are multiple elements, you can choose to do something specific here
            // For example, you might want to return a list of all elements.
            // Here, I'm just returning the first element.
            return Optional.of(list.get(0));
        }
    }

}
