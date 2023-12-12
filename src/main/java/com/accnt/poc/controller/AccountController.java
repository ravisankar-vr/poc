package com.accnt.poc.controller;

import com.accnt.poc.entity.Account;
import com.accnt.poc.entity.Transaction;
import com.accnt.poc.exception.RecordNotFoundException;
import com.accnt.poc.response.ResponseHandler;
import com.accnt.poc.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    AccountService accountService;

    @GetMapping("/{custId}")
    public List<Account> getAccountDetails(@PathVariable("custId") int cust_Id){
        LOGGER.info("In AccountService RestController - to fetch Account Details based on Customer-Id");
        List<Account> accountList=accountService.getAccounts(cust_Id);

        return accountList;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> createAccount(@RequestBody Account account) {
        LOGGER.info("In AccountService RestController - To Save Account Details");
        Map<String, String> response =  new HashMap<String, String>();

        Optional<Account> account1= Optional.ofNullable(accountService.createAccount(account));
        if (account1.isEmpty()) {
            //return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            response.put("msg", "NOT saved any Account in DB");
        }

        response.put("msg", "Account saved successfully");

        return new ResponseEntity<Object>(response, HttpStatus.OK);

    }

    @PostMapping("/transfer")
    public ResponseEntity<Object> transferMoney(@RequestParam("sourceAccountId") int sourceAccountId,
                                                @RequestParam("targetAccountId") int targetAccountId,
                                                @RequestParam("amount") Double amount) {
        LOGGER.info("In AccountService RestController - to Transfer Amount from one A/C to another A/C");
        Optional<Transaction> Transaction1= Optional.ofNullable(accountService.transferMoney(sourceAccountId, targetAccountId, amount));
       // return ResponseEntity.ok("Money transferred successfully");

        if (Transaction1.isEmpty()) {
            //return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            throw new RecordNotFoundException("NOT transfered any amount from the Account");
        }

        return ResponseHandler.responseBuilder("Transfered amount successfully",
                HttpStatus.OK,
                Transaction1);
    }


}
