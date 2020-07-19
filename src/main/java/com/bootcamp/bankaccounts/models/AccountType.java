package com.bootcamp.bankaccounts.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "accountType")
public class AccountType {
    @Id
    private String idAccountType;
    private String name;
    private Double minAmount;
    private Double minBalance; //saldo mínimo al final de mes
}