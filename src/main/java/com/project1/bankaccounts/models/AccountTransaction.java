package com.project1.bankaccounts.models;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accountTransaction")
public class AccountTransaction {
    @Id
    private String idAccountTransaction;
    private Double amount;
    private BankAccount bankAccount;
    private TransactionType transactionType;
    private Date creationDate;

    public AccountTransaction() {
    }

    public AccountTransaction(String idAccountTransaction, Double amount, BankAccount bankAccount, TransactionType transactionType, Date creationDate) {
        this.idAccountTransaction = idAccountTransaction;
        this.amount = amount;
        this.bankAccount = bankAccount;
        this.transactionType = transactionType;
        this.creationDate = creationDate;
    }

    public String getIdAccountTransaction() {
        return this.idAccountTransaction;
    }

    public void setIdAccountTransaction(String idAccountTransaction) {
        this.idAccountTransaction = idAccountTransaction;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public BankAccount getBankAccount() {
        return this.bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public TransactionType getTransactionType() {
        return this.transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "{" +
            " idAccountTransaction='" + getIdAccountTransaction() + "'" +
            ", amount='" + getAmount() + "'" +
            ", bankAccount='" + getBankAccount() + "'" +
            ", transactionType='" + getTransactionType() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}