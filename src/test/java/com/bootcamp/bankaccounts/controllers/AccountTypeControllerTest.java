package com.bootcamp.bankaccounts.controllers;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import com.bootcamp.bankaccounts.models.AccountType;
import com.bootcamp.bankaccounts.services.AccountTypeService;

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
    AccountTypeService accountTypeService;

    @Autowired
    private WebTestClient webclient;
    
    @Test
    public void getAllAccountTypes() {
        AccountType accountType = new AccountType("1", "ahorro",0.0,0.0);

        List<AccountType> list = new ArrayList<AccountType>();
        list.add(accountType);
         
        Flux<AccountType> accountFlux = Flux.fromIterable(list);

        Mockito
            .when(accountTypeService.findAll())
            .thenReturn(accountFlux);

        webclient.get()
            .uri("/account/types")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(AccountType.class);

            Mockito.verify(accountTypeService, times(1)).findAll();
    }

    @Test
    public void newAccountType() {
        AccountType accountType = new AccountType("1", "ahorro",0.0,0.0);

        Mockito
            .when(accountTypeService.save(accountType))
            .thenReturn(Mono.just(accountType));
        
        webclient.post()
            .uri("/account/type/new")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(accountType))
            .exchange()
            .expectStatus().isOk()
            .expectBody(AccountType.class);

        Mockito.verify(accountTypeService, times(1)).save(refEq(accountType));
    }

    @Test
    public void deleteAccountType() {
        AccountType accountType = new AccountType("1", "ahorro",0.0,0.0);

        Mockito
            .when(accountTypeService.findById("1"))
            .thenReturn(Mono.just(accountType));

        Mono<Void> voidReturn  = Mono.empty();
        Mockito
            .when(accountTypeService.delete(accountType))
            .thenReturn(voidReturn);

	    webclient.delete()
                .uri("/account/type/{typeId}", accountType.getIdAccountType())
                .exchange()
                .expectStatus().isOk();
    }
}