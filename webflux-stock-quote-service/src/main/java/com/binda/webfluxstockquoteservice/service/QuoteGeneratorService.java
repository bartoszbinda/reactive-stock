package com.binda.webfluxstockquoteservice.service;

import com.binda.webfluxstockquoteservice.model.Quote;
import reactor.core.publisher.Flux;

import java.time.Duration;

public interface QuoteGeneratorService {
    Flux<Quote> fetchQuoteStream(Duration period);
}
