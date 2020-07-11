package com.project1.bankaccounts.controllers;

import com.project1.bankaccounts.models.Transaction;
import com.project1.bankaccounts.repositories.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class TransactionController {
    
    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping(value = "/transactions")
    public @ResponseBody Flux<Transaction> getAllTransactions() {
        // list all data in transaction collection
        return transactionRepository.findAll();
    }

    @PostMapping(value = "/transaction/new")
    public Mono<Transaction> newTransaction(@RequestBody Transaction newTransaction) {
        // adding a new transaction to the collection
        return transactionRepository.save(newTransaction);
    }

    @PutMapping(value = "/transaction/{transactionId}")
    public Mono <ResponseEntity<Transaction>> updateTransaction(@PathVariable(name = "transactionId") String transactionId, @RequestBody Transaction transaction) {
        return transactionRepository.findById(transactionId)
            .flatMap(existingTransaction -> {
                return transactionRepository.save(transaction);
            })
            .map(updateTransaction -> new ResponseEntity<>(updateTransaction, HttpStatus.OK))
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/transaction/{transactionId}")
    public Mono<ResponseEntity<Void>> deleteTransactionId(@PathVariable(name = "transactionId") String transactionId) {
        return transactionRepository.findById(transactionId)
            .flatMap(existingTransaction ->
                transactionRepository.delete(existingTransaction)
                    .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))) 
            )
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}