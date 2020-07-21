package com.bootcamp.bankaccounts.repositories;

import java.util.Date;

import com.bootcamp.bankaccounts.models.Commission;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

@Repository
public interface CommissionRepository extends ReactiveMongoRepository<Commission, String> {
    
    @Query("{ 'creationDate': { $gte: ?0,  $lte: ?1} }")
    public Flux<Commission> getCommissionsByDate(Date initialDate, Date endDate);
}