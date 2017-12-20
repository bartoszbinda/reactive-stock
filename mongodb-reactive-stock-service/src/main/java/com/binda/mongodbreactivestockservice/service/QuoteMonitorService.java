package com.binda.mongodbreactivestockservice.service;

import com.binda.mongodbreactivestockservice.client.StockQuoteClient;
import com.binda.mongodbreactivestockservice.domain.Quote;
import com.binda.mongodbreactivestockservice.repositories.QuoteRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class QuoteMonitorService implements ApplicationListener<ContextRefreshedEvent> {
    private final StockQuoteClient stockQuoteClient;
    private  final QuoteRepository quoteRepository;
    public QuoteMonitorService(StockQuoteClient stockQuoteClient, QuoteRepository quoteRepository) {
        this.stockQuoteClient = stockQuoteClient;
        this.quoteRepository = quoteRepository;
    }


    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        stockQuoteClient.getQuoteStream()
                .log("quote-monitor-service")
                .subscribe(quote ->{
                    Mono<Quote> savedQuote = quoteRepository.save(quote);
                    System.out.println(savedQuote.block().getId());
                });
    }
}
