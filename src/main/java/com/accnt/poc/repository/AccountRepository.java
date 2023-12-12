package com.accnt.poc.repository;

import com.accnt.poc.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query(nativeQuery = true, value = "Select * from bnk.account where cust_id=?1")
    List<Account> findByCustId(Integer id);
}
