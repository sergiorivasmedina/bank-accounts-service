package com.project1.bankaccounts.controllers;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import com.project1.bankaccounts.models.AccountType;
import com.project1.bankaccounts.repositories.AccountTypeRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = AccountTypeController.class)
public class AccountTypeControllerTest {

    @MockBean
    AccountTypeRepository accountTypeRepository;

    @Autowired
    private WebTestClient webclient;
    
    @Test
    public void getAllAccountTypes() {
        AccountType accountType = new AccountType("1", "ahorro");

        List<AccountType> list = new ArrayList<AccountType>();
        list.add(accountType);
         
        Flux<AccountType> accountFlux = Flux.fromIterable(list);

        Mockito
            .when(accountTypeRepository.findAll())
            .thenReturn(accountFlux);

        webclient.get()
            .uri("/account/types")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(AccountType.class);

            Mockito.verify(accountTypeRepository, times(1)).findAll();
    }

    @Test
    public void newAccountType() {
        AccountType accountType = new AccountType("1", "ahorro");

        Mockito
            .when(accountTypeRepository.save(accountType))
            .thenReturn(Mono.just(accountType));
        
        webclient.post()
            .uri("/account/type/new")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(accountType))
            .exchange()
            .expectStatus().isOk()
            .expectBody(AccountType.class);

        Mockito.verify(accountTypeRepository, times(1)).save(refEq(accountType));
    }

    @Test
    public void deleteAccountType() {
        AccountType accountType = new AccountType("1", "ahorro");

        Mockito
            .when(accountTypeRepository.findById("1"))
            .thenReturn(Mono.just(accountType));

        Mono<Void> voidReturn  = Mono.empty();
        Mockito
            .when(accountTypeRepository.delete(accountType))
            .thenReturn(voidReturn);

	    webclient.delete()
                .uri("/account/type/{typeId}", accountType.getIdAccountType())
                .exchange()
                .expectStatus().isOk();
    }
}