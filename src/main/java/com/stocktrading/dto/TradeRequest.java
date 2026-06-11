package com.stocktrading.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

// Request DTO for submitting a buy or sell trade order.
public class TradeRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Stock symbol is required")
    private String stockSymbol;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    public TradeRequest() {
    }

    public TradeRequest(String username, String stockSymbol, int quantity) {
        this.username = username;
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
