package com.cust.poc.entity;


import com.cust.poc.enum2.TransactionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;

@Embeddable
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    public Transaction(String sourceAccountId, String targetAccountId, Double amount,
                       TransactionType transaction_type, LocalDateTime transaction_date) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.amount = amount;
        this.transaction_type = transaction_type;
        this.transaction_date = transaction_date;
    }

    private Integer id;

    @JsonProperty("source_account_id")
    private String sourceAccountId;

    @JsonProperty("target_account_id")
    private String targetAccountId;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("transaction_type")
    @Enumerated(EnumType.STRING)
    private TransactionType transaction_type;

    @JsonProperty("transaction_date")
    private LocalDateTime transaction_date;


}
