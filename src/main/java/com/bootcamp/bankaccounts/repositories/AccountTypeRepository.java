package com.bootcamp.bankaccounts.repositories;

import com.bootcamp.bankaccounts.models.AccountType;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTypeRepository extends ReactiveMongoRepository<AccountType, String> {
    
}