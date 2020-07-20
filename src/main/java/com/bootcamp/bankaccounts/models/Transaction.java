package com.bootcamp.bankaccounts.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transaction")
public class Transaction {
    @Id
    private String idAccountTransaction;
    private Double amount;
    private String transactionType;
    
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date creationDate;

    public Transaction(Double amount, String transactionType, Date creationDate) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.creationDate = creationDate;
    }

    public Transaction() {
    }

    public Transaction(String idAccountTransaction, Double amount, String transactionType, Date creationDate) {
        this.idAccountTransaction = idAccountTransaction;
        this.amount = amount;
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

    public String getTransactionType() {
        return this.transactionType;
    }

    public void setTransactionType(String transactionType) {
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
            ", transactionType='" + getTransactionType() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}