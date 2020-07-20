package com.bootcamp.bankaccounts.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditTransactionDTO {
    private String idCreditTransaction;
    private Double amount;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date creationDate;
    private String transactionType;

    public CreditTransactionDTO(Double amount, Date creationDate, String transactionType){
        this.amount = amount;
        this.creationDate = creationDate;
        this.transactionType = transactionType;
    }
}