package com.bootcamp.bankaccounts.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transactionType")
public class TransactionType {
    @Id
    private String idTransactionType;
    private String name;

    public TransactionType() {
    }

    public TransactionType(String idTransactionType, String name) {
        this.idTransactionType = idTransactionType;
        this.name = name;
    }

    public String getIdTransactionType() {
        return this.idTransactionType;
    }

    public void setIdTransactionType(String idTransactionType) {
        this.idTransactionType = idTransactionType;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{" +
            " idTransactionType='" + getIdTransactionType() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}