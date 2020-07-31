package com.bootcamp.bankaccounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankDTO {
    private String idBank;
    private String name;
    private double depositFromOtherBankCommission;
    private double withdrawFromOtherBankCommission;
}