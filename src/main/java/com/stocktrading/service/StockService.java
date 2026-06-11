package com.stocktrading.service;

import com.stocktrading.exception.ResourceNotFoundException;
import com.stocktrading.model.Stock;
import com.stocktrading.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service handling stock CRUD operations and lookup.
 */
@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    /**
     * Add a new stock to the market.
     *
     * @param stock the stock to add
     * @return the persisted stock
     * @throws IllegalArgumentException if the symbol already exists
     */
    @Transactional
    public Stock addStock(Stock stock) {
        if (stockRepository.existsBySymbol(stock.getSymbol().toUpperCase())) {
            throw new IllegalArgumentException("Stock with symbol '" + stock.getSymbol() + "' already exists");
        }
        stock.setSymbol(stock.getSymbol().toUpperCase());
        stock.setPreviousClose(stock.getCurrentPrice());
        return stockRepository.save(stock);
    }

    /**
     * Retrieve all stocks currently listed on the platform.
     */
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    /**
     * Find a stock by its ticker symbol.
     *
     * @param symbol the stock ticker (e.g., AAPL)
     * @return the found stock
     * @throws ResourceNotFoundException if no stock exists with the given symbol
     */
    public Stock getStockBySymbol(String symbol) {
        return stockRepository.findBySymbol(symbol.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found: " + symbol));
    }
}
