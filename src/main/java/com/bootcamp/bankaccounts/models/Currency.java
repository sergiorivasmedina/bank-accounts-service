package com.bootcamp.bankaccounts.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "currency")
public class Currency {
    @Id
    private String idCurrency;
    private String name;
    private String symbol;

    public Currency() {
    }

    public Currency(String idCurrency, String name, String symbol) {
        this.idCurrency = idCurrency;
        this.name = name;
        this.symbol = symbol;
    }

    public String getIdCurrency() {
        return this.idCurrency;
    }

    public void setIdCurrency(String idCurrency) {
        this.idCurrency = idCurrency;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "{" +
            " idCurrency='" + getIdCurrency() + "'" +
            ", name='" + getName() + "'" +
            ", symbol='" + getSymbol() + "'" +
            "}";
    }
}