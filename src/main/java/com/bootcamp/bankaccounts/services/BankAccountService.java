package com.bootcamp.bankaccounts.services;

import com.bootcamp.bankaccounts.models.BankAccount;
import com.bootcamp.bankaccounts.repositories.BankAccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BankAccountService {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    public Flux<BankAccount> findAll() {
        return bankAccountRepository.findAll();
    }

    public Mono<BankAccount> save(BankAccount newAccount) {
        return bankAccountRepository.save(newAccount);
    }

    public Mono<BankAccount> findById(String accountId) {
        return bankAccountRepository.findById(accountId);
    }

    public Mono<Void> delete(BankAccount BankAccount) {
        return bankAccountRepository.delete(BankAccount);
    }

    public Flux<BankAccount> searchAccountsByCustomerId(String customerId) {
        return bankAccountRepository.findByIdCustomer(customerId);
    }

    public Mono<String> bankTranfer(String originId, String destinyId, Double amount) {

        return bankAccountRepository.findById(originId)
                .filter(originAccount -> originAccount.getAvailableBalance() > amount)
                .flatMap(originAccount -> {
                    originAccount.setAvailableBalance(originAccount.getAvailableBalance() - amount);

                    //search if exist destiny Account
                    return bankAccountRepository.findById(destinyId)
                        .flatMap(destinyAccount -> {
                            destinyAccount.setAvailableBalance(destinyAccount.getAvailableBalance() + amount);

                            //save accounts
                            bankAccountRepository.save(originAccount).subscribe();
                            return bankAccountRepository.save(destinyAccount)
                                    .thenReturn("Transferencia Exitosa!");
                        })
                        .switchIfEmpty(Mono.just("No se encontró cuenta de destino."));
                })
                .switchIfEmpty(Mono.just("No se encontró cuenta origen."));
    }
}