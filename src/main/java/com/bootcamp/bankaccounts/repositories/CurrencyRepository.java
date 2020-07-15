package com.bootcamp.bankaccounts.repositories;

import com.bootcamp.bankaccounts.models.Currency;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends ReactiveMongoRepository<Currency, String> {
    
}