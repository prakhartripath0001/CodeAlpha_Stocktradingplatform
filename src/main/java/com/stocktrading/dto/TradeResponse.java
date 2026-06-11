package com.stocktrading.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// Response DTO returned after a trade (buy or sell) is executed.
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

    public TradeResponse() {
    }

    public TradeResponse(String message, String type, String username, String stockSymbol,
                         String companyName, int quantity, BigDecimal pricePerShare,
                         BigDecimal totalAmount, BigDecimal remainingBalance, LocalDateTime transactionDate) {
        this.message = message;
        this.type = type;
        this.username = username;
        this.stockSymbol = stockSymbol;
        this.companyName = companyName;
        this.quantity = quantity;
        this.pricePerShare = pricePerShare;
        this.totalAmount = totalAmount;
        this.remainingBalance = remainingBalance;
        this.transactionDate = transactionDate;
    }

    // Builder pattern
    public static TradeResponseBuilder builder() {
        return new TradeResponseBuilder();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPricePerShare() {
        return pricePerShare;
    }

    public void setPricePerShare(BigDecimal pricePerShare) {
        this.pricePerShare = pricePerShare;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(BigDecimal remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    // Manual builder class
    public static class TradeResponseBuilder {
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

        public TradeResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public TradeResponseBuilder type(String type) {
            this.type = type;
            return this;
        }

        public TradeResponseBuilder username(String username) {
            this.username = username;
            return this;
        }

        public TradeResponseBuilder stockSymbol(String stockSymbol) {
            this.stockSymbol = stockSymbol;
            return this;
        }

        public TradeResponseBuilder companyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public TradeResponseBuilder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public TradeResponseBuilder pricePerShare(BigDecimal pricePerShare) {
            this.pricePerShare = pricePerShare;
            return this;
        }

        public TradeResponseBuilder totalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public TradeResponseBuilder remainingBalance(BigDecimal remainingBalance) {
            this.remainingBalance = remainingBalance;
            return this;
        }

        public TradeResponseBuilder transactionDate(LocalDateTime transactionDate) {
            this.transactionDate = transactionDate;
            return this;
        }

        public TradeResponse build() {
            return new TradeResponse(message, type, username, stockSymbol, companyName,
                    quantity, pricePerShare, totalAmount, remainingBalance, transactionDate);
        }
    }
}
