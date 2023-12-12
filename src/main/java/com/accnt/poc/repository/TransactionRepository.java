package com.accnt.poc.repository;

import com.accnt.poc.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query(nativeQuery = true, value = "Select * from bnk.transaction where cust_id=?1")
    List<Transaction> findByCustId(Integer id);
}
