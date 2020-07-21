package com.bootcamp.bankaccounts.controllers;

import com.bootcamp.bankaccounts.dto.CommissionDTO;
import com.bootcamp.bankaccounts.models.Commission;
import com.bootcamp.bankaccounts.services.CommissionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class CommissionController {
    
    @Autowired
    private CommissionService commissionService;

    @GetMapping(value = "/accounts/commissions")
    public Flux<Commission> getCommissionsByDate(@RequestBody CommissionDTO commissionDTO) {
        return commissionService.getCommissionsByDate(commissionDTO);
    }
}