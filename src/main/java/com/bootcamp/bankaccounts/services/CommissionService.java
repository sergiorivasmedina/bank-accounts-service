package com.bootcamp.bankaccounts.services;

import com.bootcamp.bankaccounts.dto.CommissionDTO;
import com.bootcamp.bankaccounts.models.Commission;
import com.bootcamp.bankaccounts.repositories.CommissionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CommissionService {
    @Autowired
    private CommissionRepository commissionRepository;

    public Mono<Commission> save(Commission commission) {
        return commissionRepository.save(commission);
    }

    public Flux<Commission> getCommissionsByDate(CommissionDTO commissionDTO) {
        return commissionRepository.getCommissionsByDate(commissionDTO.getInitialDate(), commissionDTO.getEndDate());
    }
}