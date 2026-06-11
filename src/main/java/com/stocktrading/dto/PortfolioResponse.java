package com.stocktrading.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Response DTO representing a user's complete portfolio view,
 * including individual holdings and overall performance metrics.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioResponse {

    private String username;
    private BigDecimal cashBalance;
    private BigDecimal totalInvested;
    private BigDecimal currentPortfolioValue;
    private BigDecimal totalGainLoss;
    private BigDecimal totalGainLossPercentage;
    private List<HoldingDetail> holdings;

    /**
     * Detail for a single stock holding within the portfolio.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class HoldingDetail {
        private String stockSymbol;
        private String companyName;
        private int quantity;
        private BigDecimal averageBuyPrice;
        private BigDecimal currentPrice;
        private BigDecimal investedValue;
        private BigDecimal currentValue;
        private BigDecimal gainLoss;
        private BigDecimal gainLossPercentage;
    }
}
