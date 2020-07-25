package com.bootcamp.bankaccounts.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditDTO {
    private String idCredit;
    private String idCustomer;
    private String idCurrency;
    private Double availableAmount;
    private Double consumedAmount;
    private String creditType;
    private Double limit;
    private int expiredDebt;
    private List<String> creditTransactions;
    private String bankId;
}