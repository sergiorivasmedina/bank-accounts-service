package com.bootcamp.bankaccounts.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "commission")
public class Commission {
    @Id
    private String idCommission;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date creationDate;
    private String idTransaction;
    private String idBankAccount;

    public Commission(Date creationDate, String idTransaction, String idBankAccount) {
        this.creationDate = creationDate;
        this.idTransaction = idTransaction;
        this.idBankAccount = idBankAccount;
    }
}