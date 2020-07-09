package com.project1.bankaccounts.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "authorizedSignatory")
public class AuthorizedSignatory {
    @Id
    private String idAuthorizedSignatory;
    private List<String> customers;

    public AuthorizedSignatory() {
    }

    public AuthorizedSignatory(String idAuthorizedSignatory, List<String> customers) {
        this.idAuthorizedSignatory = idAuthorizedSignatory;
        this.customers = customers;
    }

    public String getIdAuthorizedSignatory() {
        return this.idAuthorizedSignatory;
    }

    public void setIdAuthorizedSignatory(String idAuthorizedSignatory) {
        this.idAuthorizedSignatory = idAuthorizedSignatory;
    }

    public List<String> getCustomers() {
        return this.customers;
    }

    public void setCustomers(List<String> customers) {
        this.customers = customers;
    }

    @Override
    public String toString() {
        return "{" +
            " idAuthorizedSignatory='" + getIdAuthorizedSignatory() + "'" +
            ", customers='" + getCustomers() + "'" +
            "}";
    }
}