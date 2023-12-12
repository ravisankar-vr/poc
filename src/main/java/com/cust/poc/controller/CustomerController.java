package com.cust.poc.controller;

import com.cust.poc.entity.Customer;
import com.cust.poc.exception.RecordNotFoundException;
import com.cust.poc.response.ResponseHandler;
import com.cust.poc.service.CustomerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    /**
     * This API is to fetch all Customer Details
     * @return
     */
    @GetMapping(value="/")
    public ResponseEntity<Object> getCustomerDetails() {
        LOGGER.info("In CustomerService RestController - to fetch All Customer Details");
        List<Customer> customers=customerService.getCustomers();
        if (customers.isEmpty()) {
            //return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            throw new RecordNotFoundException("No Customers Exists in DB");
        }

        //return ResponseEntity.status(HttpStatus.OK).body(customers);
        return ResponseHandler.responseBuilder("List of all Customers",
                HttpStatus.OK,
                customers);

    }

    /**
     * This API is to fetch Customer details based on customerId
     * @param cust_Id
     * @return
     */
    @GetMapping(value="/{custId}")
    public ResponseEntity<Object> getCustomerById(@PathVariable("custId") int cust_Id) {
        LOGGER.info("In CustomerService RestController - to fetch Customer Details based on Customer-Id ");

        Optional<Customer> customers=customerService.getCustomerById(cust_Id);
        if (customers.isEmpty()) {
            //return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            throw new RecordNotFoundException("No Customers Exists in DB");
        }

        return ResponseHandler.responseBuilder("Fetch Customer Details by ID : "+cust_Id,
                HttpStatus.OK,
                customers);

    }

    @PostMapping(value="/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createCustomer(@Valid @RequestBody Customer customer){

        LOGGER.info("In CustomerService RestController - To Save Customer Details");
        Optional<Customer> customers= Optional.ofNullable(customerService.createCustomer(customer));
        if (customers.isEmpty()) {
            //return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            throw new RecordNotFoundException("NOT saved any Customer in DB");
        }

        return ResponseHandler.responseBuilder("Save Customer Details",
                HttpStatus.OK,
                customers);
    }

}
