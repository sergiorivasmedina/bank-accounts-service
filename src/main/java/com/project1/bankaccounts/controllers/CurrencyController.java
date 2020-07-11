package com.project1.bankaccounts.controllers;

import com.project1.bankaccounts.models.Currency;
import com.project1.bankaccounts.repositories.CurrencyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CurrencyController {
    
    @Autowired
    private CurrencyRepository currencyRepository;

    @GetMapping(value = "/currencies")
    public @ResponseBody Flux<Currency> getAllCurrencies() {
        // list all data in currency collection
        return currencyRepository.findAll();
    }

    @PostMapping(value = "/currency/new")
    public Mono<Currency> newCurrency(@RequestBody Currency newCurrency) {
        // adding a new currency to the collection
        return currencyRepository.save(newCurrency);
    }

    @PutMapping(value = "/currency/{currencyId}")
    public Mono<ResponseEntity<Currency>> updateCurrency(@PathVariable(name = "currencyId") String currencyId, @RequestBody Currency currency) {
        return currencyRepository.findById(currencyId)
            .flatMap(existingCurrency -> {
                return currencyRepository.save(currency);
            })
            .map(updateCurrency -> new ResponseEntity<>(updateCurrency, HttpStatus.OK))
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/currency/{currencyId}")
    public Mono<ResponseEntity<Void>> deleteCurrency(@PathVariable(name = "currencyId") String currencyId) {
        return currencyRepository.findById(currencyId)
            .flatMap(existingCurrency ->
                currencyRepository.delete(existingCurrency)
                    .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))) 
            )
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}