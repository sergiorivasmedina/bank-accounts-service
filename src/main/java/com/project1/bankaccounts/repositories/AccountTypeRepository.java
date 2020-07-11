package com.project1.bankaccounts.repositories;

import com.project1.bankaccounts.models.AccountType;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTypeRepository extends ReactiveMongoRepository<AccountType, String> {
    
}