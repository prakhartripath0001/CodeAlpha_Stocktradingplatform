package com.stocktrading.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Response DTO returned after a trade (buy or sell) is executed.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeResponse {

    private String message;
    private String type;
    private String username;
    private String stockSymbol;
    private String companyName;
    private int quantity;
    private BigDecimal pricePerShare;
    private BigDecimal totalAmount;
    private BigDecimal remainingBalance;
    private LocalDateTime transactionDate;
}
