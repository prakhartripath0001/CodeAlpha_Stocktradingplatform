package com.stocktrading.service;

import com.stocktrading.dto.TradeRequest;
import com.stocktrading.dto.TradeResponse;
import com.stocktrading.exception.InsufficientFundsException;
import com.stocktrading.exception.InsufficientSharesException;
import com.stocktrading.exception.ResourceNotFoundException;
import com.stocktrading.model.*;
import com.stocktrading.repository.PortfolioRepository;
import com.stocktrading.repository.TransactionRepository;
import com.stocktrading.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Core trading engine that executes buy and sell orders.
 * Handles balance validation, portfolio updates, and
 * transaction recording within a single database transaction.
 */
@Service
public class TradeService {

    private final UserService userService;
    private final StockService stockService;
    private final PortfolioRepository portfolioRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TradeService(UserService userService,
                        StockService stockService,
                        PortfolioRepository portfolioRepository,
                        TransactionRepository transactionRepository,
                        UserRepository userRepository) {
        this.userService = userService;
        this.stockService = stockService;
        this.portfolioRepository = portfolioRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    /**
     * Execute a BUY order: deduct cash, add shares to portfolio,
     * and record the transaction.
     *
     * @param request the trade request with username, symbol, and quantity
     * @return confirmation response with trade details
     */
    @Transactional
    public TradeResponse buyStock(TradeRequest request) {
        User user = userService.getUserByUsername(request.getUsername());
        Stock stock = stockService.getStockBySymbol(request.getStockSymbol());

        BigDecimal totalCost = stock.getCurrentPrice()
                .multiply(BigDecimal.valueOf(request.getQuantity()));

        // Validate sufficient funds
        if (user.getBalance().compareTo(totalCost) < 0) {
            throw new InsufficientFundsException(
                    "Insufficient funds. Required: $" + totalCost.setScale(2, RoundingMode.HALF_UP)
                            + ", Available: $" + user.getBalance().setScale(2, RoundingMode.HALF_UP));
        }

        // Deduct balance
        user.setBalance(user.getBalance().subtract(totalCost));
        userRepository.save(user);

        // Update or create portfolio holding
        Optional<Portfolio> existingHolding = portfolioRepository
                .findByUserIdAndStockId(user.getId(), stock.getId());

        if (existingHolding.isPresent()) {
            Portfolio portfolio = existingHolding.get();
            // Recalculate weighted average buy price
            BigDecimal oldTotal = portfolio.getAverageBuyPrice()
                    .multiply(BigDecimal.valueOf(portfolio.getQuantity()));
            int newQuantity = portfolio.getQuantity() + request.getQuantity();
            BigDecimal newAvg = oldTotal.add(totalCost)
                    .divide(BigDecimal.valueOf(newQuantity), 2, RoundingMode.HALF_UP);
            portfolio.setQuantity(newQuantity);
            portfolio.setAverageBuyPrice(newAvg);
            portfolioRepository.save(portfolio);
        } else {
            Portfolio portfolio = new Portfolio();
            portfolio.setUser(user);
            portfolio.setStock(stock);
            portfolio.setQuantity(request.getQuantity());
            portfolio.setAverageBuyPrice(stock.getCurrentPrice());
            portfolioRepository.save(portfolio);
        }

        // Record transaction
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setStock(stock);
        transaction.setType(TransactionType.BUY);
        transaction.setQuantity(request.getQuantity());
        transaction.setPricePerShare(stock.getCurrentPrice());
        transaction.setTotalAmount(totalCost);
        transactionRepository.save(transaction);

        // Update stock volume
        stock.setVolume(stock.getVolume() + request.getQuantity());

        return TradeResponse.builder()
                .message("Successfully bought " + request.getQuantity() + " shares of " + stock.getSymbol())
                .type("BUY")
                .username(user.getUsername())
                .stockSymbol(stock.getSymbol())
                .companyName(stock.getCompanyName())
                .quantity(request.getQuantity())
                .pricePerShare(stock.getCurrentPrice())
                .totalAmount(totalCost)
                .remainingBalance(user.getBalance())
                .transactionDate(LocalDateTime.now())
                .build();
    }

    /**
     * Execute a SELL order: add cash, remove shares from portfolio,
     * and record the transaction.
     *
     * @param request the trade request with username, symbol, and quantity
     * @return confirmation response with trade details
     */
    @Transactional
    public TradeResponse sellStock(TradeRequest request) {
        User user = userService.getUserByUsername(request.getUsername());
        Stock stock = stockService.getStockBySymbol(request.getStockSymbol());

        // Validate holding exists and has enough shares
        Portfolio portfolio = portfolioRepository
                .findByUserIdAndStockId(user.getId(), stock.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "You do not own any shares of " + stock.getSymbol()));

        if (portfolio.getQuantity() < request.getQuantity()) {
            throw new InsufficientSharesException(
                    "Insufficient shares. You own " + portfolio.getQuantity()
                            + " shares of " + stock.getSymbol()
                            + " but tried to sell " + request.getQuantity());
        }

        BigDecimal totalProceeds = stock.getCurrentPrice()
                .multiply(BigDecimal.valueOf(request.getQuantity()));

        // Credit balance
        user.setBalance(user.getBalance().add(totalProceeds));
        userRepository.save(user);

        // Update portfolio
        int remainingShares = portfolio.getQuantity() - request.getQuantity();
        if (remainingShares == 0) {
            portfolioRepository.delete(portfolio);
        } else {
            portfolio.setQuantity(remainingShares);
            portfolioRepository.save(portfolio);
        }

        // Record transaction
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setStock(stock);
        transaction.setType(TransactionType.SELL);
        transaction.setQuantity(request.getQuantity());
        transaction.setPricePerShare(stock.getCurrentPrice());
        transaction.setTotalAmount(totalProceeds);
        transactionRepository.save(transaction);

        // Update stock volume
        stock.setVolume(stock.getVolume() + request.getQuantity());

        return TradeResponse.builder()
                .message("Successfully sold " + request.getQuantity() + " shares of " + stock.getSymbol())
                .type("SELL")
                .username(user.getUsername())
                .stockSymbol(stock.getSymbol())
                .companyName(stock.getCompanyName())
                .quantity(request.getQuantity())
                .pricePerShare(stock.getCurrentPrice())
                .totalAmount(totalProceeds)
                .remainingBalance(user.getBalance())
                .transactionDate(LocalDateTime.now())
                .build();
    }
}
