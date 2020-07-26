package com.bootcamp.bankaccounts.dto;

import java.util.Date;
import java.util.List;

import com.bootcamp.bankaccounts.models.BankAccount;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private String idBankAccount;
    private Double availableBalance;
    private String accountTypeName;
    private Double minAmount;
    private Double minBalance;
    private List<String> customers;
    private int numberTransactionsRemainder;
    private double commission;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date createdAt;

    public AccountDTO(BankAccount bankAccount, String accountTypeName, Double minAmount, Double minBalance) {
        this.idBankAccount = bankAccount.getIdBankAccount();
        this.availableBalance = bankAccount.getAvailableBalance();
        this.customers = bankAccount.getIdCustomer();
        this.numberTransactionsRemainder = bankAccount.getNumberTransactionsRemainder();
        this.commission = bankAccount.getCommission();
        this.createdAt = bankAccount.getCreatedAt();
        //Account type info
        this.accountTypeName = accountTypeName;
        this.minAmount = minAmount;
        this.minBalance = minBalance;
    }
}