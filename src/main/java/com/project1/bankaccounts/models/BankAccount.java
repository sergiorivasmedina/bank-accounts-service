package com.project1.bankaccounts.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bankAccount")
public class BankAccount {
    @Id
    private String idBankAccount;
    private double availableBalance;
    private String accountType;
    private String currency;
    private List<String> idCustomer; //Spanish: Titulares de la cuenta -> Customers
    private List<String> authorizedSignatories; //Spanish: Firmantes autorizados -> Customers
    private List<String> transactions;

    public BankAccount() {
    }

    public BankAccount(String idBankAccount, double availableBalance, String accountType, String currency, List<String> idCustomer, List<String> authorizedSignatories, List<String> transactions) {
        this.idBankAccount = idBankAccount;
        this.availableBalance = availableBalance;
        this.accountType = accountType;
        this.currency = currency;
        this.idCustomer = idCustomer;
        this.authorizedSignatories = authorizedSignatories;
        this.transactions = transactions;
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

    public String getAccountType() {
        return this.accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<String> getIdCustomer() {
        return this.idCustomer;
    }

    public void setIdCustomer(List<String> idCustomer) {
        this.idCustomer = idCustomer;
    }

    public List<String> getAuthorizedSignatories() {
        return this.authorizedSignatories;
    }

    public void setAuthorizedSignatories(List<String> authorizedSignatories) {
        this.authorizedSignatories = authorizedSignatories;
    }

    public List<String> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(List<String> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "{" +
            " idBankAccount='" + getIdBankAccount() + "'" +
            ", availableBalance='" + getAvailableBalance() + "'" +
            ", accountType='" + getAccountType() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", idCustomer='" + getIdCustomer() + "'" +
            ", authorizedSignatories='" + getAuthorizedSignatories() + "'" +
            ", transactions='" + getTransactions() + "'" +
            "}";
    }
}