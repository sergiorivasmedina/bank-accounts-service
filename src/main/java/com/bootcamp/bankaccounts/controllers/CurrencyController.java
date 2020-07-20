package com.bootcamp.bankaccounts.controllers;

import com.bootcamp.bankaccounts.models.Currency;
import com.bootcamp.bankaccounts.services.CurrencyService;

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
    private CurrencyService currencyService;

    @GetMapping(value = "/currencies")
    public @ResponseBody Flux<Currency> getAllCurrencies() {
        // list all data in currency collection
        return currencyService.findAll();
    }

    @PostMapping(value = "/currency/new")
    public Mono<Currency> newCurrency(@RequestBody Currency newCurrency) {
        // adding a new currency to the collection
        return currencyService.save(newCurrency);
    }

    @PutMapping(value = "/currency/{currencyId}")
    public Mono<ResponseEntity<Currency>> updateCurrency(@PathVariable(name = "currencyId") String currencyId, @RequestBody Currency currency) {
        return currencyService.findById(currencyId)
            .flatMap(existingCurrency -> {
                return currencyService.save(currency);
            })
            .map(updateCurrency -> new ResponseEntity<>(updateCurrency, HttpStatus.OK))
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/currency/{currencyId}")
    public Mono<ResponseEntity<Void>> deleteCurrency(@PathVariable(name = "currencyId") String currencyId) {
        return currencyService.findById(currencyId)
            .flatMap(existingCurrency ->
                currencyService.delete(existingCurrency)
                    .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))) 
            )
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/currency/search/{currencyId}")
    public Mono<String> getCurrencyName(@PathVariable(name = "currencyId") String currencyId) {
        return currencyService.findById(currencyId)
                .map(Currency::getName)
                .defaultIfEmpty("No se encontró moneda");
    }
}