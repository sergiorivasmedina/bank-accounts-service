package com.bootcamp.bankaccounts.services;

import com.bootcamp.bankaccounts.models.Currency;
import com.bootcamp.bankaccounts.repositories.CurrencyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CurrencyService {
    @Autowired
    private CurrencyRepository currencyRepository;

    public Flux<Currency> findAll() {
        return currencyRepository.findAll();
    }

    public Mono<Currency> save(Currency newAccount) {
        return currencyRepository.save(newAccount);
    }

    public Mono<Currency> findById(String accountId) {
        return currencyRepository.findById(accountId);
    }

    public Mono<Void> delete(Currency currency) {
        return currencyRepository.delete(currency);
    }
}