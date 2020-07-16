package com.bootcamp.bankaccounts.controllers;

import com.bootcamp.bankaccounts.models.TransactionType;
import com.bootcamp.bankaccounts.services.TransactionTypeService;

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
public class TransactionTypeController {
    
    @Autowired
    private TransactionTypeService transactionTypeService;

    @GetMapping(value = "/transaction/types")
    public @ResponseBody Flux<TransactionType> getAllTrasactionTypes() {
        // list all data in trasanction type collection
        return transactionTypeService.findAll();
    }

    @PostMapping(value = "/transaction/type/new")
    public Mono<TransactionType> newTransactionType(@RequestBody TransactionType newType) {
        // adding a new credit to the collection
        return transactionTypeService.save(newType);
    }

    @PutMapping(value = "/transaction/type/{typeId}")
    public Mono<ResponseEntity<TransactionType>> updateTransactionType(@PathVariable(name = "typeId") String typeId, @RequestBody TransactionType type) {
        return transactionTypeService.findById(typeId)
            .flatMap(existingType -> {
                return transactionTypeService.save(type);
            })
            .map(updateType -> new ResponseEntity<>(updateType, HttpStatus.OK))
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/transaction/type/{typeId}")
    public Mono<ResponseEntity<Void>> deleteCreditType(@PathVariable(name = "typeId") String typeId) {
        return transactionTypeService.findById(typeId)
            .flatMap(existingType ->
                transactionTypeService.delete(existingType)
                    .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))) 
            )
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}