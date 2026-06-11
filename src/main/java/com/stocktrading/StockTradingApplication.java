package com.stocktrading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main entry point for the Stock Trading Platform application.
 * Enables scheduling for the market price simulator.
 */
@SpringBootApplication
@EnableScheduling
public class StockTradingApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockTradingApplication.class, args);
    }
}
