package com.stocktrading.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for submitting a buy or sell trade order.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Stock symbol is required")
    private String stockSymbol;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
}
