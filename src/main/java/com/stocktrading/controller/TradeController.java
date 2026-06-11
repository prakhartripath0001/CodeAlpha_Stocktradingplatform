package com.stocktrading.controller;

import com.stocktrading.dto.TradeRequest;
import com.stocktrading.dto.TradeResponse;
import com.stocktrading.service.TradeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for executing buy and sell trade orders.
 */
@RestController
@RequestMapping("/api/trades")
public class TradeController {

    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    /**
     * Buy shares of a stock.
     * POST /api/trades/buy
     * Body: { "username": "...", "stockSymbol": "...", "quantity": ... }
     */
    @PostMapping("/buy")
    public ResponseEntity<TradeResponse> buyStock(@Valid @RequestBody TradeRequest request) {
        return ResponseEntity.ok(tradeService.buyStock(request));
    }

    /**
     * Sell shares of a stock.
     * POST /api/trades/sell
     * Body: { "username": "...", "stockSymbol": "...", "quantity": ... }
     */
    @PostMapping("/sell")
    public ResponseEntity<TradeResponse> sellStock(@Valid @RequestBody TradeRequest request) {
        return ResponseEntity.ok(tradeService.sellStock(request));
    }
}
