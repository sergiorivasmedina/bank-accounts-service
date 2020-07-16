package com.bootcamp.bankaccounts.services;

import com.bootcamp.bankaccounts.models.TransactionType;
import com.bootcamp.bankaccounts.repositories.TransactionTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionTypeService {
    @Autowired
    private TransactionTypeRepository transactionTypeRepository;

    public Flux<TransactionType> findAll() {
        return transactionTypeRepository.findAll();
    }

    public Mono<TransactionType> save(TransactionType newType) {
        return transactionTypeRepository.save(newType);
    }

    public Mono<TransactionType> findById(String typeId) {
        return transactionTypeRepository.findById(typeId);
    }

    public Mono<Void> delete(TransactionType transactionType) {
        return transactionTypeRepository.delete(transactionType);
    }
}