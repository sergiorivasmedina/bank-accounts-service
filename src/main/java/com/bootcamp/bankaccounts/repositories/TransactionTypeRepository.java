package com.bootcamp.bankaccounts.repositories;

import com.bootcamp.bankaccounts.models.TransactionType;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTypeRepository extends ReactiveMongoRepository<TransactionType, String> {
    
}