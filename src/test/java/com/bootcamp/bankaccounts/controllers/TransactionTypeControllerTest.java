package com.bootcamp.bankaccounts.controllers;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import com.bootcamp.bankaccounts.models.TransactionType;
import com.bootcamp.bankaccounts.repositories.TransactionTypeRepository;

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
@WebFluxTest(controllers = TransactionTypeController.class)
public class TransactionTypeControllerTest {
    // @MockBean
    // TransactionTypeRepository repository;

    // @Autowired
    // private WebTestClient webclient;
    
    // @Test
    // public void getAllTrasactionTypes() {
    //     TransactionType transactionType = new TransactionType("1", "deposito");

    //     List<TransactionType> list = new ArrayList<TransactionType>();
    //     list.add(transactionType);
         
    //     Flux<TransactionType> transactionTypeFlux = Flux.fromIterable(list);

    //     Mockito
    //         .when(repository.findAll())
    //         .thenReturn(transactionTypeFlux);

    //     webclient.get()
    //         .uri("/transaction/types")
    //         .accept(MediaType.APPLICATION_JSON)
    //         .exchange()
    //         .expectStatus().isOk()
    //         .expectBodyList(TransactionType.class);

    //         Mockito.verify(repository, times(1)).findAll();
    // }

    // @Test
    // public void newTransactionType() {
    //     TransactionType transactionType = new TransactionType("1", "deposito");

    //     Mockito
    //         .when(repository.save(transactionType))
    //         .thenReturn(Mono.just(transactionType));
        
    //     webclient.post()
    //         .uri("/transaction/type/new")
    //         .contentType(MediaType.APPLICATION_JSON)
    //         .body(BodyInserters.fromValue(transactionType))
    //         .exchange()
    //         .expectStatus().isOk()
    //         .expectBody(TransactionType.class);

    //     Mockito.verify(repository, times(1)).save(refEq(transactionType));
    // }

    // @Test
    // public void deleteTransactionType() {
    //     TransactionType transactionType = new TransactionType("1", "deposito");

    //     Mockito
    //         .when(repository.findById("1"))
    //         .thenReturn(Mono.just(transactionType));

    //     Mono<Void> voidReturn  = Mono.empty();
    //     Mockito
    //         .when(repository.delete(transactionType))
    //         .thenReturn(voidReturn);

	//     webclient.delete()
    //             .uri("/transaction/type/{typeId}", transactionType.getIdTransactionType())
    //             .exchange()
    //             .expectStatus().isOk();
    // }
}