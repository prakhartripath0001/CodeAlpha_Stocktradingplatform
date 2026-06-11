package com.stocktrading.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Entity representing a user's holding of a specific stock.
 * Tracks the quantity held and the average purchase price
 * to enable gain/loss calculations.
 */
@Entity
@Table(name = "portfolios", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "stock_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    /** Number of shares currently held. */
    @Column(nullable = false)
    private Integer quantity;

    /** Weighted average price at which the shares were bought. */
    @Column(name = "average_buy_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal averageBuyPrice;
}
