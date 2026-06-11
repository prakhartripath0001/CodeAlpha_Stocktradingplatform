package com.stocktrading.controller;

import com.stocktrading.dto.PortfolioResponse;
import com.stocktrading.service.PortfolioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for viewing portfolio performance.
 */
@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    /**
     * Get a user's portfolio with performance metrics.
     * GET /api/portfolio/{username}
     */
    @GetMapping("/{username}")
    public ResponseEntity<PortfolioResponse> getPortfolio(@PathVariable String username) {
        return ResponseEntity.ok(portfolioService.getPortfolio(username));
    }
}
