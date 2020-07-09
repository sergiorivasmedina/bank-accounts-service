package com.project1.bankaccounts.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bankAccount")
public class BankAccount {
    @Id
    private String idBankAccount;
    private double availableBalance;
    private AccountType accountType;
    private List<String> accountHolder;
    private AuthorizedSignatory authorizedSignatories;

    public BankAccount() {
    }

    public BankAccount(String idBankAccount, double availableBalance, AccountType accountType, List<String> accountHolder, AuthorizedSignatory authorizedSignatories) {
        this.idBankAccount = idBankAccount;
        this.availableBalance = availableBalance;
        this.accountType = accountType;
        this.accountHolder = accountHolder;
        this.authorizedSignatories = authorizedSignatories;
    }

    public String getIdBankAccount() {
        return this.idBankAccount;
    }

    public void setIdBankAccount(String idBankAccount) {
        this.idBankAccount = idBankAccount;
    }

    public double getAvailableBalance() {
        return this.availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public AccountType getAccountType() {
        return this.accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public List<String> getAccountHolder() {
        return this.accountHolder;
    }

    public void setAccountHolder(List<String> accountHolder) {
        this.accountHolder = accountHolder;
    }

    public AuthorizedSignatory getAuthorizedSignatories() {
        return this.authorizedSignatories;
    }

    public void setAuthorizedSignatories(AuthorizedSignatory authorizedSignatories) {
        this.authorizedSignatories = authorizedSignatories;
    }

    @Override
    public String toString() {
        return "{" +
            " idBankAccount='" + getIdBankAccount() + "'" +
            ", availableBalance='" + getAvailableBalance() + "'" +
            ", accountType='" + getAccountType() + "'" +
            ", accountHolder='" + getAccountHolder() + "'" +
            ", authorizedSignatories='" + getAuthorizedSignatories() + "'" +
            "}";
    }
}