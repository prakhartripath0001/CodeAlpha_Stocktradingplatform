package com.stocktrading.service;

import com.stocktrading.model.Stock;
import com.stocktrading.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

/**
 * Scheduled component that simulates live market price movements
 * using a random walk algorithm. Runs every 30 seconds, adjusting
 * each stock's price by a small random percentage to mimic
 * real-world market fluctuations.
 */
@Component
public class MarketSimulator {

    private static final Logger log = LoggerFactory.getLogger(MarketSimulator.class);
    private static final double MAX_CHANGE_PERCENT = 2.0; // max +/- 2% per tick

    private final StockRepository stockRepository;
    private final Random random = new Random();

    public MarketSimulator(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    /**
     * Periodically update all stock prices with a random walk.
     * Each stock's price changes by -2% to +2% per cycle.
     * Prices are clamped to a minimum of $1.00 to prevent
     * stocks from reaching zero or going negative.
     */
    @Scheduled(fixedRate = 30000) // every 30 seconds
    @Transactional
    public void simulateMarket() {
        List<Stock> stocks = stockRepository.findAll();
        if (stocks.isEmpty()) {
            return;
        }

        for (Stock stock : stocks) {
            // Save current price as previous close
            stock.setPreviousClose(stock.getCurrentPrice());

            // Random change between -MAX_CHANGE_PERCENT and +MAX_CHANGE_PERCENT
            double changePercent = (random.nextDouble() * 2 - 1) * MAX_CHANGE_PERCENT;
            BigDecimal multiplier = BigDecimal.ONE.add(
                    BigDecimal.valueOf(changePercent / 100.0));

            BigDecimal newPrice = stock.getCurrentPrice()
                    .multiply(multiplier)
                    .setScale(2, RoundingMode.HALF_UP);

            // Floor at $1.00 to prevent unrealistic prices
            if (newPrice.compareTo(BigDecimal.ONE) < 0) {
                newPrice = BigDecimal.ONE;
            }

            stock.setCurrentPrice(newPrice);

            log.debug("Stock {} price updated: {} -> {} ({}%)",
                    stock.getSymbol(),
                    stock.getPreviousClose(),
                    stock.getCurrentPrice(),
                    String.format("%.2f", changePercent));
        }

        stockRepository.saveAll(stocks);
        log.info("Market simulation tick completed. {} stocks updated.", stocks.size());
    }
}
