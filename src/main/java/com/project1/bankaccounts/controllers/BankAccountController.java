package com.project1.bankaccounts.controllers;

import java.util.List;

import com.project1.bankaccounts.models.AccountTransaction;
import com.project1.bankaccounts.models.BankAccount;
import com.project1.bankaccounts.repositories.AccountTransactionRepository;
import com.project1.bankaccounts.repositories.BankAccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class BankAccountController {
    
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private AccountTransactionRepository accountTransactionRepository;

    @GetMapping(value = "/accounts")
    public @ResponseBody List<BankAccount> getAllAccounts() {
        // get all accounts from BankAccount collection
        return bankAccountRepository.findAll();
    }

    @PutMapping(value = "/account/new")
    public @ResponseBody BankAccount createAccount(@RequestBody BankAccount bankAccount) {
        // this method create a new bank account
        return bankAccountRepository.save(bankAccount);
    }

    @GetMapping(value = "/transactions")
    public @ResponseBody List<AccountTransaction> getAlltransactions() {
        // get all accounts from AccountTrasanction collection
        return accountTransactionRepository.findAll();
    }

    @PutMapping(value = "/transaction/new")
    public @ResponseBody AccountTransaction createTransaction(@RequestBody AccountTransaction transaction) {
        // this method create a new trasaction for a bank account
        return accountTransactionRepository.save(transaction);
    }
}