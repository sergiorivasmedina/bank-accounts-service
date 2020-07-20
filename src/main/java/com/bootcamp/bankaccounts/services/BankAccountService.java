package com.bootcamp.bankaccounts.services;

import java.util.List;

import com.bootcamp.bankaccounts.models.BankAccount;
import com.bootcamp.bankaccounts.repositories.BankAccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BankAccountService {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    public Flux<BankAccount> findAll() {
        return bankAccountRepository.findAll();
    }

    public Mono<BankAccount> save(BankAccount newAccount) {
        return bankAccountRepository.save(newAccount);
    }

    public Mono<BankAccount> findById(String accountId) {
        return bankAccountRepository.findById(accountId);
    }

    public Mono<Void> delete(BankAccount BankAccount) {
        return bankAccountRepository.delete(BankAccount);
    }

    public Flux<BankAccount> searchAccountsByCustomerId(String customerId) {
        return bankAccountRepository.findByIdCustomer(customerId);
    }
}