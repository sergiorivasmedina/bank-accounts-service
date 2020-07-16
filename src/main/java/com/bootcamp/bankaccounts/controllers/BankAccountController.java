package com.bootcamp.bankaccounts.controllers;

import com.bootcamp.bankaccounts.models.BankAccount;
import com.bootcamp.bankaccounts.models.Transaction;
import com.bootcamp.bankaccounts.services.BankAccountService;
import com.bootcamp.bankaccounts.services.TransactionService;

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
public class BankAccountController {
    
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private TransactionService transactionService;

    @GetMapping(value = "/accounts")
    public @ResponseBody Flux<BankAccount> getAllAccounts() {
        // list all data in bank account collection
        return bankAccountService.findAll();
    }

    @PostMapping(value = "/account/new")
    public Mono<BankAccount> newAccount(@RequestBody BankAccount newAccount) {
        // adding a new bank Account to the collection
        return bankAccountService.save(newAccount);
    }

    @PutMapping(value = "/account/{accountId}")
    public Mono<ResponseEntity<BankAccount>> updateAccount(@PathVariable(name = "accountId") String accountId, @RequestBody BankAccount account) {
        return bankAccountService.findById(accountId)
            .flatMap(existingAccount -> {
                return bankAccountService.save(account);
            })
            .map(updateAccount -> new ResponseEntity<>(updateAccount, HttpStatus.OK))
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/account/{accountId}")
    public Mono<ResponseEntity<Void>> deleteAccount(@PathVariable(name = "accountId") String accountId) {
        return bankAccountService.findById(accountId)
            .flatMap(existingAccount ->
                bankAccountService.delete(existingAccount)
                    .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))) 
            )
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //Deposit money
    @PutMapping(value = "/account/deposit/{accountId}/{amount}")
    public Mono<ResponseEntity<BankAccount>> deposit(@PathVariable(name = "accountId") String accountId, @PathVariable(name = "amount") Double amount) {
        return bankAccountService.findById(accountId)
            .flatMap(existingAccount -> {
                //update availableBalance
                existingAccount.setAvailableBalance(existingAccount.getAvailableBalance() + amount);
                return bankAccountService.save(existingAccount);
            })
            .map(updateAccount -> new ResponseEntity<>(updateAccount, HttpStatus.OK))
            .defaultIfEmpty((new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    //withdraw money
    @PutMapping(value = "/account/withdraw/{accountId}/{amount}")
    public Mono<ResponseEntity<BankAccount>> withdraw(@PathVariable(name = "accountId") String accountId, @PathVariable(name = "amount") Double amount) {
        return bankAccountService.findById(accountId)
            .flatMap(existingAccount -> {
                //update availableBalance
                existingAccount.setAvailableBalance(existingAccount.getAvailableBalance() - amount);
                return bankAccountService.save(existingAccount);
            })
            .map(updateAccount -> new ResponseEntity<>(updateAccount, HttpStatus.OK))
            .defaultIfEmpty((new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    //check balance
    @GetMapping(value = "/account/check/balance/{accountId}")
    public Mono<ResponseEntity<Double>> checkBalance(@PathVariable(name = "accountId") String accountId) {
        return bankAccountService.findById(accountId)
                .map(account -> new ResponseEntity<>(account.getAvailableBalance(), HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //check transactions
    @GetMapping(value = "/account/check/transactions/{accountId}")
    public Flux<Transaction> checkTransactions(@PathVariable(name = "accountId") String accountId) {
        Mono<BankAccount> bankAccount = bankAccountService.findById(accountId);
        Flux<String> transactions = bankAccount
                                        .map(BankAccount::getTransactions)
                                        .flatMapMany((Flux::fromIterable));
        
        return transactions.flatMap(tran -> {
            return transactionService.findById(tran);
        }).collectList().flatMapMany(Flux::fromIterable);
    }
}