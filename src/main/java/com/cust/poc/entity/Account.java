package com.cust.poc.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;



@Embeddable
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="ACCOUNT")
public class Account {
    public Account(String accNo, Integer accBal, List<Transaction> transactions) {
        this.accNo = accNo;
        this.accBal = accBal;
        this.transactions = transactions;
    }

    @JsonIgnore
    @Id
    private Integer Id;

    @JsonProperty("account_number")
    private String accNo;

    @JsonProperty("balance")
    private Integer accBal;

    @JsonProperty("transactions")
    @ElementCollection
    private List<Transaction> transactions;

}
