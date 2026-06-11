package com.stocktrading.service;

import com.stocktrading.dto.PortfolioResponse;
import com.stocktrading.model.Portfolio;
import com.stocktrading.model.User;
import com.stocktrading.repository.PortfolioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Service that computes portfolio performance metrics for a user,
 * including per-holding gain/loss and overall portfolio value.
 */
@Service
public class PortfolioService {

    private final UserService userService;
    private final PortfolioRepository portfolioRepository;

    public PortfolioService(UserService userService, PortfolioRepository portfolioRepository) {
        this.userService = userService;
        this.portfolioRepository = portfolioRepository;
    }

    /**
     * Build a complete portfolio report for a given user.
     * Calculates invested value, current value, gain/loss,
     * and gain/loss percentage for each holding and in aggregate.
     *
     * @param username the user whose portfolio to retrieve
     * @return portfolio response with full performance metrics
     */
    public PortfolioResponse getPortfolio(String username) {
        User user = userService.getUserByUsername(username);
        List<Portfolio> holdings = portfolioRepository.findByUserId(user.getId());

        List<PortfolioResponse.HoldingDetail> holdingDetails = new ArrayList<>();
        BigDecimal totalInvested = BigDecimal.ZERO;
        BigDecimal totalCurrentValue = BigDecimal.ZERO;

        for (Portfolio holding : holdings) {
            BigDecimal investedValue = holding.getAverageBuyPrice()
                    .multiply(BigDecimal.valueOf(holding.getQuantity()));
            BigDecimal currentValue = holding.getStock().getCurrentPrice()
                    .multiply(BigDecimal.valueOf(holding.getQuantity()));
            BigDecimal gainLoss = currentValue.subtract(investedValue);
            BigDecimal gainLossPercent = BigDecimal.ZERO;

            if (investedValue.compareTo(BigDecimal.ZERO) > 0) {
                gainLossPercent = gainLoss
                        .divide(investedValue, 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100))
                        .setScale(2, RoundingMode.HALF_UP);
            }

            holdingDetails.add(PortfolioResponse.HoldingDetail.builder()
                    .stockSymbol(holding.getStock().getSymbol())
                    .companyName(holding.getStock().getCompanyName())
                    .quantity(holding.getQuantity())
                    .averageBuyPrice(holding.getAverageBuyPrice())
                    .currentPrice(holding.getStock().getCurrentPrice())
                    .investedValue(investedValue.setScale(2, RoundingMode.HALF_UP))
                    .currentValue(currentValue.setScale(2, RoundingMode.HALF_UP))
                    .gainLoss(gainLoss.setScale(2, RoundingMode.HALF_UP))
                    .gainLossPercentage(gainLossPercent)
                    .build());

            totalInvested = totalInvested.add(investedValue);
            totalCurrentValue = totalCurrentValue.add(currentValue);
        }

        BigDecimal totalGainLoss = totalCurrentValue.subtract(totalInvested);
        BigDecimal totalGainLossPercent = BigDecimal.ZERO;
        if (totalInvested.compareTo(BigDecimal.ZERO) > 0) {
            totalGainLossPercent = totalGainLoss
                    .divide(totalInvested, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100))
                    .setScale(2, RoundingMode.HALF_UP);
        }

        return PortfolioResponse.builder()
                .username(user.getUsername())
                .cashBalance(user.getBalance().setScale(2, RoundingMode.HALF_UP))
                .totalInvested(totalInvested.setScale(2, RoundingMode.HALF_UP))
                .currentPortfolioValue(totalCurrentValue.setScale(2, RoundingMode.HALF_UP))
                .totalGainLoss(totalGainLoss.setScale(2, RoundingMode.HALF_UP))
                .totalGainLossPercentage(totalGainLossPercent)
                .holdings(holdingDetails)
                .build();
    }
}
