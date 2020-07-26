package com.bootcamp.bankaccounts.services;

import java.util.Date;

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

    public Mono<String> bankTransfer(String originId, String destinyId, Double amount) {

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

    public Flux<BankAccount> getAccountsBetweenDates(Date initialDate, Date endDate, String bankId) {
        return bankAccountRepository.findAll()
            .filter(account -> account.getBankId() != null && account.getBankId().compareTo(bankId) == 0)
            .filter(account -> account.getCreatedAt() != null)
            .filter(account -> account.getCreatedAt().compareTo(initialDate) > 0 && account.getCreatedAt().compareTo(endDate) < 0);
    }
}