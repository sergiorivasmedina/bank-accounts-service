package com.project1.bankaccounts.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accountType")
public class AccountType {
    @Id
    private String idAccountType;
    private String name;

    public AccountType() {
    }

    public AccountType(String idAccountType, String name) {
        this.idAccountType = idAccountType;
        this.name = name;
    }

    public String getIdAccountType() {
        return this.idAccountType;
    }

    public void setIdAccountType(String idAccountType) {
        this.idAccountType = idAccountType;
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
            " idAccountType='" + getIdAccountType() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}