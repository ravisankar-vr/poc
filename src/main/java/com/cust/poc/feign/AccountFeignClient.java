package com.cust.poc.feign;

import com.cust.poc.config.FeignConfiguration;
import com.cust.poc.entity.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="account-service", url = "http://localhost:8084/v1", configuration = FeignConfiguration.class)
public interface AccountFeignClient {

    @GetMapping("/api/account/{accntId}")
    public List<Account> getAccountDetails(@PathVariable("accntId") int accnt_Id);
}
