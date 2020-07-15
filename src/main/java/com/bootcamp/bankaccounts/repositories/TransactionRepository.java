package com.bootcamp.bankaccounts.repositories;

import com.bootcamp.bankaccounts.models.Transaction;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {
    
}