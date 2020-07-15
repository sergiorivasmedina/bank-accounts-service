package com.bootcamp.bankaccounts.repositories;

import com.bootcamp.bankaccounts.models.BankAccount;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends ReactiveMongoRepository<BankAccount, String> {
    
}