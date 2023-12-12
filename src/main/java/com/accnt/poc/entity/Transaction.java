package com.accnt.poc.entity;


import com.accnt.poc.enum2.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Embeddable
@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="TRANSACTION")
public class Transaction {

    public Transaction(String sourceAccountId, String targetAccountId, Double amount,
                       TransactionType transaction_type, LocalDateTime transaction_date,Integer custId) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.amount = amount;
        this.transaction_type = transaction_type;
        this.transaction_date = transaction_date;
        this.custId = custId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRN_ID")
    private Integer id;

    @JsonIgnore
    @Column(name = "CUST_ID")
    private Integer custId;

    @JsonIgnore
    @Column(name = "ACC_ID")
    private Integer accId;

    @JsonProperty("source_account_id")
    @Column(name = "SRC_ACC_ID")
    private String sourceAccountId;

    @JsonProperty("target_account_id")
    @Column(name = "TRG_ACC_ID")
    private String targetAccountId;

    @JsonProperty("amount")
    @Column(name = "AMOUNT")
    private Double amount;

    @JsonProperty("transaction_type")
    @Column(name = "TRN_TYP")
    @Enumerated(EnumType.STRING)
    private TransactionType transaction_type;

    @JsonProperty("transaction_date")
    @Column(name = "TRN_DTE")
    private LocalDateTime transaction_date;


}
