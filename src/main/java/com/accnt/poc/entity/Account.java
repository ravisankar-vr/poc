package com.accnt.poc.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="ACCOUNT")
public class Account {

    public Account(Integer cust_Id, String accNo, Double accBal, List<Transaction> transactions) {
        this.cust_Id = cust_Id;
        this.accNo = accNo;
        this.accBal = accBal;
        this.transactions = transactions;
    }

    @Id
    @Column(name = "ACC_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(name = "CUST_ID")
    @JsonProperty("cust_Id")
    private Integer cust_Id;

    @JsonProperty("account_number")
    @Column(name = "ACC_NO")
    @NotNull
    private String accNo;

    @JsonProperty("balance")
    @Column(name = "ACC_BAL")
    @NotNull
    private Double accBal;

    @JsonProperty("transactions")
    @ElementCollection
    private List<Transaction> transactions;

}
