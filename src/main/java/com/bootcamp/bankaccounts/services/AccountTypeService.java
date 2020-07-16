package com.bootcamp.bankaccounts.services;

import com.bootcamp.bankaccounts.models.AccountType;
import com.bootcamp.bankaccounts.repositories.AccountTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountTypeService {
    
    @Autowired
    private AccountTypeRepository accountTypeRepository;

    public Flux<AccountType> findAll() {
        return accountTypeRepository.findAll();
    }

    public Mono<AccountType> save(AccountType newType) {
        return accountTypeRepository.save(newType);
    }

    public Mono<AccountType> findById(String typeId) {
        return accountTypeRepository.findById(typeId);
    }

    public Mono<Void> delete(AccountType accountType) {
        return accountTypeRepository.delete(accountType);
    }
}