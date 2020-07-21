package com.bootcamp.bankaccounts.controllers;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import com.bootcamp.bankaccounts.models.Currency;
import com.bootcamp.bankaccounts.services.CurrencyService;

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
@WebFluxTest(controllers = CurrencyController.class)
public class CurrencyControllerTest {
    @MockBean
    CurrencyService service;

    @Autowired
    private WebTestClient webclient;
    
    @Test
    public void getAllCurrencies() {
        Currency currency = new Currency("1","soles","S/");

        List<Currency> list = new ArrayList<Currency>();
        list.add(currency);
         
        Flux<Currency> transactionTypeFlux = Flux.fromIterable(list);

        Mockito
            .when(service.findAll())
            .thenReturn(transactionTypeFlux);

        webclient.get()
            .uri("/currencies")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(Currency.class);

            Mockito.verify(service, times(1)).findAll();
    }

    @Test
    public void newCurrency() {
        Currency currency = new Currency("1","soles","S/");

        Mockito
            .when(service.save(currency))
            .thenReturn(Mono.just(currency));
        
        webclient.post()
            .uri("/currency/new")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(currency))
            .exchange()
            .expectStatus().isOk()
            .expectBody(Currency.class);

        Mockito.verify(service, times(1)).save(refEq(currency));
    }

    @Test
    public void deleteAccountType() {
        Currency currency = new Currency("1","soles","S/");

        Mockito
            .when(service.findById("1"))
            .thenReturn(Mono.just(currency));

        Mono<Void> voidReturn  = Mono.empty();
        Mockito
            .when(service.delete(currency))
            .thenReturn(voidReturn);

	    webclient.delete()
                .uri("/currency/{currencyId}", currency.getIdCurrency())
                .exchange()
                .expectStatus().isOk();
    }
}