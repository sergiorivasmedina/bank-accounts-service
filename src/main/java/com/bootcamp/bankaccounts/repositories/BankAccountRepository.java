package com.bootcamp.bankaccounts.repositories;

import java.util.List;

import com.bootcamp.bankaccounts.models.BankAccount;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

@Repository
public interface BankAccountRepository extends ReactiveMongoRepository<BankAccount, String> {
    
    @Query("{ 'idCustomer': ?0 }")
    Flux<BankAccount> findByIdCustomer(String customerId);
}