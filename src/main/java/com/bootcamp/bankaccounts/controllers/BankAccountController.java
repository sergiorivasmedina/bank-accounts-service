package com.bootcamp.bankaccounts.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.bootcamp.bankaccounts.dto.AccountDTO;
import com.bootcamp.bankaccounts.dto.BankDTO;
import com.bootcamp.bankaccounts.dto.CreditDTO;
import com.bootcamp.bankaccounts.dto.CreditTransactionDTO;
import com.bootcamp.bankaccounts.dto.InitialEndDates;
import com.bootcamp.bankaccounts.models.BankAccount;
import com.bootcamp.bankaccounts.models.Commission;
import com.bootcamp.bankaccounts.models.Transaction;
import com.bootcamp.bankaccounts.services.BankAccountService;
import com.bootcamp.bankaccounts.services.CommissionService;
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
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

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
    @Autowired
    private CommissionService commissionService;

    private final String creditsUri = "localhost:8083";
    private final String banksUri = "localhost:8084";

    @GetMapping(value = "/accounts")
    public @ResponseBody Flux<BankAccount> getAllAccounts() {
        // list all data in bank account collection
        return bankAccountService.findAll();
    }

    @PostMapping(value = "/account/new")
    public Mono<BankAccount> newAccount(@RequestBody BankAccount newAccount) {
        return WebClient
                .create(creditsUri + "/credit/search/expired-debt/status/" + newAccount.getIdCustomer().get(0) + "/1")
                .get().retrieve().bodyToMono(CreditDTO.class)
                .map(credit -> new BankAccount("No se pudo crear porque tiene deuda expirada."))
                .switchIfEmpty(bankAccountService.save(newAccount));
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
            if (numberRemainder == 0) {
                balance -= existingAccount.getCommission();

                // add data to Commission document
                Commission commission = new Commission(Calendar.getInstance().getTime(), transactionId, accountId);
                commissionService.save(commission).subscribe();
            } else
                existingAccount.setNumberTransactionsRemainder(numberRemainder - 1);

            // update availableBalance
            existingAccount.setAvailableBalance(balance + amount);

            // update transactions
            List<String> list;
            if (existingAccount.getTransactions() != null) {
                list = existingAccount.getTransactions();
            } else {
                list = new ArrayList<String>();
            }

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
            if (numberRemainder == 0) {
                balance -= existingAccount.getCommission();

                // add data to Commission document
                Commission commission = new Commission(Calendar.getInstance().getTime(), transactionId, accountId);
                commissionService.save(commission).subscribe();
            } else
                existingAccount.setNumberTransactionsRemainder(numberRemainder - 1);

            // update availableBalance
            existingAccount.setAvailableBalance(balance - amount);

            // update transactions
            List<String> list;
            if (existingAccount.getTransactions() != null) {
                list = existingAccount.getTransactions();
            } else {
                list = new ArrayList<String>();
            }
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
    public Mono<ResponseEntity<?>> payCreditCard(@PathVariable(name = "creditId") String creditId,
        @PathVariable(name = "amount") Double amount, @PathVariable(name = "accountId") String accountId) {

        String creditsUri = "http://localhost:8083";
        String accountsUri = "http://localhost:8082";
        //Get account and validate amount vs available balance
        return bankAccountService.findById(accountId).filter(account -> account.getAvailableBalance() >= amount)
            // .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Saldo insuficiente")))
            .flatMap(account -> {
                //Create a new account transaction
                Transaction accountTransaction = new Transaction(amount, "5f09e93a66bb6e3bd0a30c79", Calendar.getInstance().getTime());
                
                //generate transaction
                return WebClient.create(accountsUri + "/transaction/new")
                    .post()
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(accountTransaction))
                    .retrieve()
                    .bodyToMono(Transaction.class);
            }).flatMap(transaction -> {
                //do withdraw for account
                WebClient.create(accountsUri + "/account/withdraw/" + accountId + "/" + amount + "/" + transaction.getIdAccountTransaction())
                    .put()
                    .retrieve()
                    .bodyToMono(BankAccount.class)
                    .subscribe();

                // make a transaction in credit service
                CreditTransactionDTO creditTransaction = new CreditTransactionDTO(amount, Calendar.getInstance().getTime(),
                "5f090fcdd060b215471ec392");

                //generate transaction id
                return WebClient.create(creditsUri + "/transaction/new")
                    .post()
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(creditTransaction))
                    .retrieve()
                    .bodyToMono(CreditTransactionDTO.class);
            }).flatMap(creditTransaction -> {
                return WebClient.create(creditsUri + "/credit/pay/" + creditId + "/" + amount + "/" + creditTransaction.getIdCreditTransaction())
                    .put()
                    .retrieve()
                    .bodyToMono(CreditDTO.class);
            }).flatMap(credit -> {
                return Mono.just(ResponseEntity.status(HttpStatus.OK).body(credit));
            });
    }

    // search account for this customerId
    @GetMapping(value = "/account/search/{customerId}")
    public Flux<BankAccount> searchAccountsByCustomerId(@PathVariable(name = "customerId") String customerId) {
        return bankAccountService.searchAccountsByCustomerId(customerId)
                .defaultIfEmpty(new BankAccount("0", 0.0, "No se encontró cuenta bancaria", "NA", null, null, null, 0,
                        0, 0.0, "", Calendar.getInstance().getTime()));
    }

    // Bank transfer
    @GetMapping(value = "/account/bank-transfer/{originAccountId}/{destinyAccountId}/{amount}")
    public Mono<String> bankTransfer(@PathVariable(name = "originAccountId") String originId,
            @PathVariable(name = "destinyAccountId") String destinyId, @PathVariable(name = "amount") Double amount) {

        return bankAccountService.bankTransfer(originId, destinyId, amount);
    }

    @PostMapping(value = "/account/search/betweenDates/{bankId}")
    public Flux<AccountDTO> getAccountsBetweenDates(@PathVariable(name = "bankId") String bankId,
            @RequestBody InitialEndDates dates) {
        return bankAccountService.getAccountsBetweenDates(dates.getInitialDate(), dates.getEndDate(), bankId);
    }

    // Deposit money form ATM
    @PutMapping(value = "/account/atm/deposit/{accountId}/{amount}/{transactionId}")
    public Mono<BankAccount> depositFromATM(@PathVariable(name = "accountId") String accountId,
            @PathVariable(name = "amount") Double amount, @PathVariable(name = "transactionId") String transactionId) {

        return bankAccountService.findById(accountId).flatMap(existingAccount -> {
            

            // validate numberOfAtmTransactions
            int numberOfAtmTransactions = existingAccount.getNumberOfAtmTransactions();
            String accountTypeId = existingAccount.getAccountType();

            return bankAccountService.getFreeAtmTransactions(accountTypeId).flatMap(accountType -> {
                Double balance = existingAccount.getAvailableBalance();
                if (numberOfAtmTransactions >= accountType.getFreeAtmTransactions()) {
                    // charge commission
                    balance -= existingAccount.getCommission();

                    // add data to Commission document
                    Commission commission = new Commission(Calendar.getInstance().getTime(), transactionId, accountId);
                    commissionService.save(commission).subscribe();
                } else {
                    // don't charge commission
                    existingAccount.setNumberOfAtmTransactions(numberOfAtmTransactions + 1);
                }

                // update availableBalance
                existingAccount.setAvailableBalance(balance + amount);

                // update transactions
                List<String> list;
                if (existingAccount.getTransactions() != null) {
                    list = existingAccount.getTransactions();
                } else {
                    list = new ArrayList<String>();
                }
                list.add(transactionId);
                existingAccount.setTransactions(list);

                return bankAccountService.save(existingAccount);
            });
            
        });
    }

    // Withdraw money form ATM
    @PutMapping(value = "/account/atm/withdraw/{accountId}/{amount}/{transactionId}")
    public Mono<BankAccount> withdrawFromATM(@PathVariable(name = "accountId") String accountId,
            @PathVariable(name = "amount") Double amount, @PathVariable(name = "transactionId") String transactionId) {

        return bankAccountService.findById(accountId).flatMap(existingAccount -> {
            

            // validate numberOfAtmTransactions
            int numberOfAtmTransactions = existingAccount.getNumberOfAtmTransactions();
            String accountTypeId = existingAccount.getAccountType();

            return bankAccountService.getFreeAtmTransactions(accountTypeId).flatMap(accountType -> {
                Double balance = existingAccount.getAvailableBalance();
                if (numberOfAtmTransactions >= accountType.getFreeAtmTransactions()) {
                    // charge commission
                    balance -= existingAccount.getCommission();

                    // add data to Commission document
                    Commission commission = new Commission(Calendar.getInstance().getTime(), transactionId, accountId);
                    commissionService.save(commission).subscribe();
                } else {
                    // don't charge commission
                    existingAccount.setNumberOfAtmTransactions(numberOfAtmTransactions + 1);
                }

                // update availableBalance
                existingAccount.setAvailableBalance(balance - amount);

                // update transactions
                List<String> list;
                if (existingAccount.getTransactions() != null) {
                    list = existingAccount.getTransactions();
                } else {
                    list = new ArrayList<String>();
                }
                list.add(transactionId);
                existingAccount.setTransactions(list);

                return bankAccountService.save(existingAccount);
            });
            
        });
    }

    // Deposit money form ATM to other bank
    @PutMapping(value = "/account/atm/otherBank/deposit/{accountId}/{amount}/{transactionId}")
    public Mono<BankAccount> depositFromOtherBankATM(@PathVariable(name = "accountId") String accountId,
            @PathVariable(name = "amount") Double amount, @PathVariable(name = "transactionId") String transactionId) {

        return bankAccountService.findById(accountId).flatMap(existingAccount -> {

            Mono<BankDTO> bankDto = WebClient.create(banksUri + "/bank/" + existingAccount.getBankId())
                                        .get()
                                        .retrieve()
                                        .bodyToMono(BankDTO.class);
            
            return bankDto.flatMap(bank -> {
                Double balance = existingAccount.getAvailableBalance();
                double bankCommission = bank.getDepositFromOtherBankCommission();

                //set commission
                Commission commission = new Commission(Calendar.getInstance().getTime(), transactionId, accountId);
                commissionService.save(commission).subscribe();

                //update availableBalance
                balance = balance - bankCommission;
                existingAccount.setAvailableBalance(balance + amount);

                // update transactions
                List<String> list;
                if (existingAccount.getTransactions() != null) {
                    list = existingAccount.getTransactions();
                } else {
                    list = new ArrayList<String>();
                }
                list.add(transactionId);
                existingAccount.setTransactions(list);

                return bankAccountService.save(existingAccount);
            });
            
        });
    }

    // Withdraw money form ATM to other bank
    @PutMapping(value = "/account/atm/otherBank/withdraw/{accountId}/{amount}/{transactionId}")
    public Mono<BankAccount> withdrawFromOtherBankATM(@PathVariable(name = "accountId") String accountId,
            @PathVariable(name = "amount") Double amount, @PathVariable(name = "transactionId") String transactionId) {

        return bankAccountService.findById(accountId).flatMap(existingAccount -> {

            Mono<BankDTO> bankDto = WebClient.create(banksUri + "/bank/" + existingAccount.getBankId())
                                        .get()
                                        .retrieve()
                                        .bodyToMono(BankDTO.class);
            
            return bankDto.flatMap(bank -> {
                Double balance = existingAccount.getAvailableBalance();
                double bankCommission = bank.getDepositFromOtherBankCommission();

                //set commission
                Commission commission = new Commission(Calendar.getInstance().getTime(), transactionId, accountId);
                commissionService.save(commission).subscribe();

                //update availableBalance
                balance = balance - bankCommission;
                existingAccount.setAvailableBalance(balance - amount);

                // update transactions
                List<String> list;
                if (existingAccount.getTransactions() != null) {
                    list = existingAccount.getTransactions();
                } else {
                    list = new ArrayList<String>();
                }
                list.add(transactionId);
                existingAccount.setTransactions(list);

                return bankAccountService.save(existingAccount);
            });
            
        });
    }
}