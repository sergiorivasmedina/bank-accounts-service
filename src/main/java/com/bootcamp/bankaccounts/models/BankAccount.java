package com.bootcamp.bankaccounts.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bankAccount")
public class BankAccount {
    @Id
    private String idBankAccount;
    private Double availableBalance;
    private String accountType;
    private String currency;
    private List<String> idCustomer; //Spanish: Titulares de la cuenta -> Customers
    private List<String> authorizedSignatories; //Spanish: Firmantes autorizados -> Customers
    private List<String> transactions;
    private int numberTransactionsRemainder;
    private double commission;
    private String bankId;

    public BankAccount(String idBankAccount) {
        this.idBankAccount = idBankAccount;
    }
}