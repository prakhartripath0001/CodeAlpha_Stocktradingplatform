package com.stocktrading.config;

import com.stocktrading.model.Stock;
import com.stocktrading.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Seeds the database with initial stock data on application startup.
 * Only inserts data if the stocks table is empty, making it safe
 * to run on subsequent restarts.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final StockRepository stockRepository;

    public DataSeeder(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void run(String... args) {
        if (stockRepository.count() > 0) {
            log.info("Stocks already seeded. Skipping data initialization.");
            return;
        }

        log.info("Seeding initial stock data...");

        seedStock("AAPL", "Apple Inc.", 189.84);
        seedStock("GOOGL", "Alphabet Inc.", 141.80);
        seedStock("MSFT", "Microsoft Corporation", 417.88);
        seedStock("AMZN", "Amazon.com Inc.", 185.07);
        seedStock("TSLA", "Tesla Inc.", 248.42);
        seedStock("META", "Meta Platforms Inc.", 493.50);
        seedStock("NVDA", "NVIDIA Corporation", 131.88);
        seedStock("NFLX", "Netflix Inc.", 628.34);
        seedStock("JPM", "JPMorgan Chase & Co.", 200.47);
        seedStock("V", "Visa Inc.", 277.38);

        log.info("Successfully seeded {} stocks.", stockRepository.count());
    }

    private void seedStock(String symbol, String companyName, double price) {
        Stock stock = new Stock();
        stock.setSymbol(symbol);
        stock.setCompanyName(companyName);
        stock.setCurrentPrice(BigDecimal.valueOf(price));
        stock.setPreviousClose(BigDecimal.valueOf(price));
        stock.setVolume(0L);
        stockRepository.save(stock);
    }
}
