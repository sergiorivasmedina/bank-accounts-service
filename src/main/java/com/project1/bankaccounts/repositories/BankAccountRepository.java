package com.project1.bankaccounts.repositories;

import com.project1.bankaccounts.models.BankAccount;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BankAccountRepository extends MongoRepository<BankAccount, Integer> {
    
}