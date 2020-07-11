package com.project1.bankaccounts.repositories;

import com.project1.bankaccounts.models.TransactionType;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTypeRepository extends ReactiveMongoRepository<TransactionType, String> {
    
}