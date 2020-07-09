package com.project1.bankaccounts.repositories;

import com.project1.bankaccounts.models.AccountTransaction;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountTransactionRepository extends MongoRepository<AccountTransaction, Integer> {
    
}