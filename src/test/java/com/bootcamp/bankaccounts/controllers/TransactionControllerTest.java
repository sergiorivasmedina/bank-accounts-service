package com.bootcamp.bankaccounts.controllers;

import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.bootcamp.bankaccounts.models.Transaction;
import com.bootcamp.bankaccounts.repositories.TransactionRepository;

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
@WebFluxTest(controllers = TransactionController.class)
public class TransactionControllerTest {
    // @MockBean
    // TransactionRepository repository;

    // @Autowired
    // private WebTestClient webclient;

    // @Test
    // public void getAllTransactions() {
    //     Calendar cal = Calendar.getInstance();
    //     cal.set(Calendar.YEAR, 2020);
    //     cal.set(Calendar.MONTH, Calendar.JULY);
    //     cal.set(Calendar.DAY_OF_MONTH, 14);
    //     Date dateRepresentation = cal.getTime();

    //     Transaction transaction = new Transaction("1", 100.0, "type", dateRepresentation);

    //     List<Transaction> list = new ArrayList<Transaction>();
    //     list.add(transaction);

    //     Flux<Transaction> transactionFlux = Flux.fromIterable(list);

    //     Mockito.when(repository.findAll()).thenReturn(transactionFlux);

    //     webclient.get().uri("/transactions").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk()
    //             .expectBodyList(Transaction.class);

    //     Mockito.verify(repository, times(1)).findAll();
    // }

    // @Test
    // public void newTransaction() {
    //     Calendar cal = Calendar.getInstance();
    //     cal.set(Calendar.YEAR, 2020);
    //     cal.set(Calendar.MONTH, Calendar.JULY);
    //     cal.set(Calendar.DAY_OF_MONTH, 14);
    //     Date dateRepresentation = cal.getTime();

    //     Transaction transaction = new Transaction("1", 100.0, "type", dateRepresentation);

    //     Mockito.when(repository.save(transaction)).thenReturn(Mono.just(transaction));

    //     webclient.post().uri("/transaction/new").contentType(MediaType.APPLICATION_JSON)
    //             .body(BodyInserters.fromValue(transaction)).exchange().expectStatus().isOk()
    //             .expectBody(Transaction.class);
    // }

    // @Test
    // public void deleteTransaction() {
    //     Calendar cal = Calendar.getInstance();
    //     cal.set(Calendar.YEAR, 2020);
    //     cal.set(Calendar.MONTH, Calendar.JULY);
    //     cal.set(Calendar.DAY_OF_MONTH, 14);
    //     Date dateRepresentation = cal.getTime();

    //     Transaction transaction = new Transaction("1", 100.0, "type", dateRepresentation);

    //     Mockito.when(repository.findById("1")).thenReturn(Mono.just(transaction));

    //     Mono<Void> voidReturn = Mono.empty();
    //     Mockito.when(repository.delete(transaction)).thenReturn(voidReturn);

    //     webclient.delete().uri("/transaction/{currencyId}", transaction.getIdAccountTransaction()).exchange()
    //             .expectStatus().isOk();
    // }
}