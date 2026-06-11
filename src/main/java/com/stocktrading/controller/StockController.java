package com.stocktrading.controller;

import com.stocktrading.model.Stock;
import com.stocktrading.service.StockService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for stock market data display and management.
 */
@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * List all available stocks with current market data.
     * GET /api/stocks
     */
    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        return ResponseEntity.ok(stockService.getAllStocks());
    }

    /**
     * Get a single stock by its ticker symbol.
     * GET /api/stocks/{symbol}
     */
    @GetMapping("/{symbol}")
    public ResponseEntity<Stock> getStock(@PathVariable String symbol) {
        return ResponseEntity.ok(stockService.getStockBySymbol(symbol));
    }

    /**
     * Add a new stock to the platform.
     * POST /api/stocks
     * Body: { "symbol": "...", "companyName": "...", "currentPrice": ... }
     */
    @PostMapping
    public ResponseEntity<Stock> addStock(@Valid @RequestBody Stock stock) {
        return new ResponseEntity<>(stockService.addStock(stock), HttpStatus.CREATED);
    }
}
