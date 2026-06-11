package com.stocktrading.controller;

import com.stocktrading.model.Transaction;
import com.stocktrading.model.User;
import com.stocktrading.repository.TransactionRepository;
import com.stocktrading.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * REST controller for viewing transaction history.
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionRepository transactionRepository;
    private final UserService userService;

    public TransactionController(TransactionRepository transactionRepository,
                                  UserService userService) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
    }

    /**
     * Get all transactions for a user, ordered by most recent first.
     * GET /api/transactions/{username}
     */
    @GetMapping("/{username}")
    public ResponseEntity<List<Map<String, Object>>> getTransactions(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        List<Transaction> transactions = transactionRepository
                .findByUserIdOrderByTransactionDateDesc(user.getId());

        List<Map<String, Object>> response = transactions.stream()
                .map(this::buildTransactionMap)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    /**
     * Build a serializable map from a Transaction entity,
     * avoiding lazy-loading issues.
     */
    private Map<String, Object> buildTransactionMap(Transaction tx) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", tx.getId());
        map.put("type", tx.getType().name());
        map.put("stockSymbol", tx.getStock().getSymbol());
        map.put("companyName", tx.getStock().getCompanyName());
        map.put("quantity", tx.getQuantity());
        map.put("pricePerShare", tx.getPricePerShare());
        map.put("totalAmount", tx.getTotalAmount());
        map.put("transactionDate", tx.getTransactionDate());
        return map;
    }
}
