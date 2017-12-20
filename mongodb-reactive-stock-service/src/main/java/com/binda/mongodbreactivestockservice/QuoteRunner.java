package com.binda.mongodbreactivestockservice;

import com.binda.mongodbreactivestockservice.client.StockQuoteClient;
import com.binda.mongodbreactivestockservice.domain.Quote;
import com.binda.mongodbreactivestockservice.repositories.QuoteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

@Component
public class QuoteRunner implements CommandLineRunner {

    private final StockQuoteClient stockQuoteClient;
    private final QuoteRepository repository;

    public QuoteRunner(StockQuoteClient stockQuoteClient, QuoteRepository repository) {
        this.stockQuoteClient = stockQuoteClient;
        this.repository = repository;
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        Flux<Quote> quoteFlux = repository.findWithTailableCursorBy();

        Disposable disposable = quoteFlux.subscribe(quote -> {
            System.out.println("#########@@@@@@@@@#########!~#@!#($*R%&@*$#&@*#&*&#@*&#@*&#*&#@*&@#& ID: " + quote.getId());
        });
        disposable.dispose();

    }
}
