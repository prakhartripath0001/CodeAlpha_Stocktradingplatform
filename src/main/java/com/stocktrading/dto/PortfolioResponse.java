package com.stocktrading.dto;

import java.math.BigDecimal;
import java.util.List;

// Response DTO representing a user's complete portfolio view,
// including individual holdings and overall performance metrics.
public class PortfolioResponse {

    private String username;
    private BigDecimal cashBalance;
    private BigDecimal totalInvested;
    private BigDecimal currentPortfolioValue;
    private BigDecimal totalGainLoss;
    private BigDecimal totalGainLossPercentage;
    private List<HoldingDetail> holdings;

    public PortfolioResponse() {
    }

    public PortfolioResponse(String username, BigDecimal cashBalance, BigDecimal totalInvested,
                             BigDecimal currentPortfolioValue, BigDecimal totalGainLoss,
                             BigDecimal totalGainLossPercentage, List<HoldingDetail> holdings) {
        this.username = username;
        this.cashBalance = cashBalance;
        this.totalInvested = totalInvested;
        this.currentPortfolioValue = currentPortfolioValue;
        this.totalGainLoss = totalGainLoss;
        this.totalGainLossPercentage = totalGainLossPercentage;
        this.holdings = holdings;
    }

    // Builder pattern
    public static PortfolioResponseBuilder builder() {
        return new PortfolioResponseBuilder();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(BigDecimal cashBalance) {
        this.cashBalance = cashBalance;
    }

    public BigDecimal getTotalInvested() {
        return totalInvested;
    }

    public void setTotalInvested(BigDecimal totalInvested) {
        this.totalInvested = totalInvested;
    }

    public BigDecimal getCurrentPortfolioValue() {
        return currentPortfolioValue;
    }

    public void setCurrentPortfolioValue(BigDecimal currentPortfolioValue) {
        this.currentPortfolioValue = currentPortfolioValue;
    }

    public BigDecimal getTotalGainLoss() {
        return totalGainLoss;
    }

    public void setTotalGainLoss(BigDecimal totalGainLoss) {
        this.totalGainLoss = totalGainLoss;
    }

    public BigDecimal getTotalGainLossPercentage() {
        return totalGainLossPercentage;
    }

    public void setTotalGainLossPercentage(BigDecimal totalGainLossPercentage) {
        this.totalGainLossPercentage = totalGainLossPercentage;
    }

    public List<HoldingDetail> getHoldings() {
        return holdings;
    }

    public void setHoldings(List<HoldingDetail> holdings) {
        this.holdings = holdings;
    }

    // Detail for a single stock holding within the portfolio.
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

        public HoldingDetail() {
        }

        public HoldingDetail(String stockSymbol, String companyName, int quantity,
                             BigDecimal averageBuyPrice, BigDecimal currentPrice,
                             BigDecimal investedValue, BigDecimal currentValue,
                             BigDecimal gainLoss, BigDecimal gainLossPercentage) {
            this.stockSymbol = stockSymbol;
            this.companyName = companyName;
            this.quantity = quantity;
            this.averageBuyPrice = averageBuyPrice;
            this.currentPrice = currentPrice;
            this.investedValue = investedValue;
            this.currentValue = currentValue;
            this.gainLoss = gainLoss;
            this.gainLossPercentage = gainLossPercentage;
        }

        // Builder pattern
        public static HoldingDetailBuilder builder() {
            return new HoldingDetailBuilder();
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

        public BigDecimal getAverageBuyPrice() {
            return averageBuyPrice;
        }

        public void setAverageBuyPrice(BigDecimal averageBuyPrice) {
            this.averageBuyPrice = averageBuyPrice;
        }

        public BigDecimal getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(BigDecimal currentPrice) {
            this.currentPrice = currentPrice;
        }

        public BigDecimal getInvestedValue() {
            return investedValue;
        }

        public void setInvestedValue(BigDecimal investedValue) {
            this.investedValue = investedValue;
        }

        public BigDecimal getCurrentValue() {
            return currentValue;
        }

        public void setCurrentValue(BigDecimal currentValue) {
            this.currentValue = currentValue;
        }

        public BigDecimal getGainLoss() {
            return gainLoss;
        }

        public void setGainLoss(BigDecimal gainLoss) {
            this.gainLoss = gainLoss;
        }

        public BigDecimal getGainLossPercentage() {
            return gainLossPercentage;
        }

        public void setGainLossPercentage(BigDecimal gainLossPercentage) {
            this.gainLossPercentage = gainLossPercentage;
        }

        // Manual builder class for HoldingDetail
        public static class HoldingDetailBuilder {
            private String stockSymbol;
            private String companyName;
            private int quantity;
            private BigDecimal averageBuyPrice;
            private BigDecimal currentPrice;
            private BigDecimal investedValue;
            private BigDecimal currentValue;
            private BigDecimal gainLoss;
            private BigDecimal gainLossPercentage;

            public HoldingDetailBuilder stockSymbol(String stockSymbol) {
                this.stockSymbol = stockSymbol;
                return this;
            }

            public HoldingDetailBuilder companyName(String companyName) {
                this.companyName = companyName;
                return this;
            }

            public HoldingDetailBuilder quantity(int quantity) {
                this.quantity = quantity;
                return this;
            }

            public HoldingDetailBuilder averageBuyPrice(BigDecimal averageBuyPrice) {
                this.averageBuyPrice = averageBuyPrice;
                return this;
            }

            public HoldingDetailBuilder currentPrice(BigDecimal currentPrice) {
                this.currentPrice = currentPrice;
                return this;
            }

            public HoldingDetailBuilder investedValue(BigDecimal investedValue) {
                this.investedValue = investedValue;
                return this;
            }

            public HoldingDetailBuilder currentValue(BigDecimal currentValue) {
                this.currentValue = currentValue;
                return this;
            }

            public HoldingDetailBuilder gainLoss(BigDecimal gainLoss) {
                this.gainLoss = gainLoss;
                return this;
            }

            public HoldingDetailBuilder gainLossPercentage(BigDecimal gainLossPercentage) {
                this.gainLossPercentage = gainLossPercentage;
                return this;
            }

            public HoldingDetail build() {
                return new HoldingDetail(stockSymbol, companyName, quantity, averageBuyPrice,
                        currentPrice, investedValue, currentValue, gainLoss, gainLossPercentage);
            }
        }
    }

    // Manual builder class for PortfolioResponse
    public static class PortfolioResponseBuilder {
        private String username;
        private BigDecimal cashBalance;
        private BigDecimal totalInvested;
        private BigDecimal currentPortfolioValue;
        private BigDecimal totalGainLoss;
        private BigDecimal totalGainLossPercentage;
        private List<HoldingDetail> holdings;

        public PortfolioResponseBuilder username(String username) {
            this.username = username;
            return this;
        }

        public PortfolioResponseBuilder cashBalance(BigDecimal cashBalance) {
            this.cashBalance = cashBalance;
            return this;
        }

        public PortfolioResponseBuilder totalInvested(BigDecimal totalInvested) {
            this.totalInvested = totalInvested;
            return this;
        }

        public PortfolioResponseBuilder currentPortfolioValue(BigDecimal currentPortfolioValue) {
            this.currentPortfolioValue = currentPortfolioValue;
            return this;
        }

        public PortfolioResponseBuilder totalGainLoss(BigDecimal totalGainLoss) {
            this.totalGainLoss = totalGainLoss;
            return this;
        }

        public PortfolioResponseBuilder totalGainLossPercentage(BigDecimal totalGainLossPercentage) {
            this.totalGainLossPercentage = totalGainLossPercentage;
            return this;
        }

        public PortfolioResponseBuilder holdings(List<HoldingDetail> holdings) {
            this.holdings = holdings;
            return this;
        }

        public PortfolioResponse build() {
            return new PortfolioResponse(username, cashBalance, totalInvested,
                    currentPortfolioValue, totalGainLoss, totalGainLossPercentage, holdings);
        }
    }
}
