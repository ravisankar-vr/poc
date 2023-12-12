package com.cust.poc.entity;

import com.cust.poc.enum2.AccountStatus;
import com.cust.poc.enum2.AccountType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Validated
@Entity
@Table(name="CUSTOMER")
@JsonRootName("customer")
public class Customer {

    public Customer(String custName, AccountType type, AccountStatus status,
                    String custPhone, String custEmail, List<Account> accounts) {
        this.custName = custName;
        this.type = type;
        this.status = status;
        this.custPhone = custPhone;
        this.custEmail = custEmail;
        this.accounts = accounts;
    }

    public Customer(String custName, AccountType type, AccountStatus status,
                    String custPhone, String custEmail) {
        this.custName = custName;
        this.type = type;
        this.status = status;
        this.custPhone = custPhone;
        this.custEmail = custEmail;
    }

    @Id
    @Column(name = "CUST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @JsonProperty("name")
    @Column(name = "CUST_NM")
    @NotNull
    private String custName;

    @JsonProperty("acc_type")
    @Enumerated(EnumType.STRING)
    @Column(name = "CUST_ACC_TYP")
    private AccountType type;

    @JsonProperty("acc_status")
    @Enumerated(EnumType.STRING)
    @Column(name = "CUST_ACC_STS")
    private AccountStatus status;

    @JsonProperty("phone")
    @NotNull
    @Column(name = "CUST_PHONE")
    private String custPhone;

    @JsonProperty("email")
    @NotNull
    @Column(name = "CUST_EMAIL")
    private String custEmail;

    @JsonProperty("account")
    @ElementCollection
    private List<Account> accounts;


}
