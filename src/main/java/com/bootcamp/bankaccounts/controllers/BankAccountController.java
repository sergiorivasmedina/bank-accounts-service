package com.bootcamp.bankaccounts.controllers;

import java.util.Calendar;
import java.util.List;

import com.bootcamp.bankaccounts.dto.CreditTransactionDTO;
import com.bootcamp.bankaccounts.models.BankAccount;
import com.bootcamp.bankaccounts.models.Transaction;
import com.bootcamp.bankaccounts.services.BankAccountService;
import com.bootcamp.bankaccounts.services.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.client.RestTemplate;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
        RequestMethod.PUT })
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
    public Mono<ResponseEntity<BankAccount>> updateAccount(@PathVariable(name = "accountId") String accountId,
            @RequestBody BankAccount account) {
        return bankAccountService.findById(accountId).flatMap(existingAccount -> {
            return bankAccountService.save(account);
        }).map(updateAccount -> new ResponseEntity<>(updateAccount, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/account/{accountId}")
    public Mono<ResponseEntity<Void>> deleteAccount(@PathVariable(name = "accountId") String accountId) {
        return bankAccountService.findById(accountId)
                .flatMap(existingAccount -> bankAccountService.delete(existingAccount)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Deposit money
    @PutMapping(value = "/account/deposit/{accountId}/{amount}/{transactionId}")
    public Mono<ResponseEntity<BankAccount>> deposit(@PathVariable(name = "accountId") String accountId,
            @PathVariable(name = "amount") Double amount, @PathVariable(name = "transactionId") String transactionId) {
        return bankAccountService.findById(accountId).flatMap(existingAccount -> {
            Double balance = existingAccount.getAvailableBalance();

            // validate numberTransactionsRemainder
            int numberRemainder = existingAccount.getNumberTransactionsRemainder();
            if (numberRemainder == 0)
                balance -= existingAccount.getCommission();
            else
                existingAccount.setNumberTransactionsRemainder(numberRemainder - 1);

            // update availableBalance
            existingAccount.setAvailableBalance(balance + amount);

            //update transactions
            List<String> list = existingAccount.getTransactions();
            list.add(transactionId);
            existingAccount.setTransactions(list);

            return bankAccountService.save(existingAccount);
        }).map(updateAccount -> new ResponseEntity<>(updateAccount, HttpStatus.OK))
                .defaultIfEmpty((new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    // withdraw money
    @PutMapping(value = "/account/withdraw/{accountId}/{amount}/{transactionId}")
    public Mono<ResponseEntity<BankAccount>> withdraw(@PathVariable(name = "accountId") String accountId,
            @PathVariable(name = "amount") Double amount, @PathVariable(name = "transactionId") String transactionId) {

        return bankAccountService.findById(accountId).flatMap(existingAccount -> {
            Double balance = existingAccount.getAvailableBalance();

            // validate numberTransactionsRemainder
            int numberRemainder = existingAccount.getNumberTransactionsRemainder();
            if (numberRemainder == 0)
                balance -= existingAccount.getCommission();
            else
                existingAccount.setNumberTransactionsRemainder(numberRemainder - 1);

            // update availableBalance
            existingAccount.setAvailableBalance(balance - amount);

            //update transactions
            List<String> list = existingAccount.getTransactions();
            list.add(transactionId);
            existingAccount.setTransactions(list);

            return bankAccountService.save(existingAccount);
        }).map(updateAccount -> new ResponseEntity<>(updateAccount, HttpStatus.OK))
                .defaultIfEmpty((new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    // check balance
    @GetMapping(value = "/account/check/balance/{accountId}")
    public Mono<ResponseEntity<Double>> checkBalance(@PathVariable(name = "accountId") String accountId) {
        return bankAccountService.findById(accountId)
                .map(account -> new ResponseEntity<>(account.getAvailableBalance(), HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // check transactions
    @GetMapping(value = "/account/check/transactions/{accountId}")
    public Flux<Transaction> checkTransactions(@PathVariable(name = "accountId") String accountId) {
        Mono<BankAccount> bankAccount = bankAccountService.findById(accountId);
        Flux<String> transactions = bankAccount.map(BankAccount::getTransactions).flatMapMany((Flux::fromIterable));

        return transactions.flatMap(tran -> {
            return transactionService.findById(tran);
        }).collectList().flatMapMany(Flux::fromIterable);
    }

    // Pay credit card
    @GetMapping(value = "/account/pay/creditCard/{creditId}/{amount}/{accountId}")
    public void payCreditCard(@PathVariable(name = "creditId") String creditId,
            @PathVariable(name = "amount") Double amount, @PathVariable(name = "accountId") String accountId) {

        String uri = "http://localhost:8083";
        String accountsUri = "http://localhost:8082";

        //make a transaction in credit service
        CreditTransactionDTO t = new CreditTransactionDTO(amount, Calendar.getInstance().getTime(), "5f090fcdd060b215471ec392");

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreditTransactionDTO> creditTrasaction = new HttpEntity<CreditTransactionDTO>(t, headers);

        CreditTransactionDTO result = restTemplate.postForObject(uri + "/transaction/new", creditTrasaction, CreditTransactionDTO.class);

        //update credit in credit service
        restTemplate.put(uri + "/credit/pay/"+ creditId + "/" + amount+"/"+ result.getIdCreditTransaction(), null);

        //Generate transaction in bank account service
        Transaction accountT = new Transaction(amount,"5f09e93a66bb6e3bd0a30c79", Calendar.getInstance().getTime());
        HttpEntity<Transaction> accountTransaction = new HttpEntity<Transaction>(accountT, headers);

        Transaction resultAccountTransaction = restTemplate.postForObject(accountsUri + "/transaction/new", accountTransaction, Transaction.class);

        //withdraw
        restTemplate.put(accountsUri + "/account/withdraw/"+ accountId +"/" + amount + "/" + resultAccountTransaction.getIdAccountTransaction(), null);
    }

    //search account for this customerId
    @GetMapping(value = "/account/search/{customerId}")
    public Flux<BankAccount> searchAccountsByCustomerId(@PathVariable(name = "customerId") String customerId){
        return bankAccountService.searchAccountsByCustomerId(customerId)
                .defaultIfEmpty(new BankAccount("0",0.0,"No se encontró cuenta bancaria", "NA",null,null,null,0,0.0));
    }
}