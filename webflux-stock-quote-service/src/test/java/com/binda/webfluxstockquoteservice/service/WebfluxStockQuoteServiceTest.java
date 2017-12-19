package com.binda.webfluxstockquoteservice.service;

import com.binda.webfluxstockquoteservice.model.Quote;
import com.binda.webfluxstockquoteservice.service.*;
import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

public class WebfluxStockQuoteServiceTest {

    private QuoteGeneratorService quoteGeneratorService = new QuoteGeneratorServiceImpl();

    @Test
    public void fetchQuoteStreamCountDown() throws Exception {
        //Get quoteFlux of quotes
        Flux<Quote> quoteFlux = quoteGeneratorService.fetchQuoteStream(Duration.ofMillis(100L));

        //subscriber lambda

        Consumer<Quote> println = System.out::println;

        //Error handler

        Consumer<Throwable> errorHandler = e -> System.out.println("Some Error occured");

        //set countdown latch to 1;
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Runnable allDone = countDownLatch::countDown;

        quoteFlux.take(10)
                .subscribe(println, errorHandler, allDone);

        countDownLatch.await();
    }
}
