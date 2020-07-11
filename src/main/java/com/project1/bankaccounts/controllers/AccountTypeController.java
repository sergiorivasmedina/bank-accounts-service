package com.project1.bankaccounts.controllers;

import com.project1.bankaccounts.models.AccountType;
import com.project1.bankaccounts.repositories.AccountTypeRepository;

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
public class AccountTypeController {
    
    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @GetMapping(value = "/account/types")
    public @ResponseBody Flux<AccountType> getAllAccountTypes() {
        // list all data in account type colection
        return accountTypeRepository.findAll();
    }

    @PostMapping(value = "/account/type/new")
    public Mono<AccountType> newAccountType(@RequestBody AccountType newtype) {
        // adding a new account type to the collection
        return accountTypeRepository.save(newtype);
    }

    @PutMapping(value = "/account/type/{typeId}")
    public Mono<ResponseEntity<AccountType>> updateAccountType(@PathVariable(name = "typeId") String typeId, @RequestBody AccountType type) {
        return accountTypeRepository.findById(typeId)
            .flatMap(existingType -> {
                return accountTypeRepository.save(type);
            })
            .map(updateType -> new ResponseEntity<>(updateType, HttpStatus.OK))
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/account/type/{typeId}")
    public Mono<ResponseEntity<Void>> deleteAccountType(@PathVariable(name = "typeId") String typeId) {
        return accountTypeRepository.findById(typeId)
            .flatMap(existingType ->
                accountTypeRepository.delete(existingType)
                    .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))) 
            )
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}