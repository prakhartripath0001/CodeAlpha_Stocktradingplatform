package com.stocktrading.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

// Entity representing a user's holding of a specific stock.
// Tracks the quantity held and the average purchase price
// to enable gain/loss calculations.
@Entity
@Table(name = "portfolios", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "stock_id"})
})
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

    // Number of shares currently held.
    @Column(nullable = false)
    private Integer quantity;

    // Weighted average price at which the shares were bought.
    @Column(name = "average_buy_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal averageBuyPrice;

    public Portfolio() {
    }

    public Portfolio(Long id, User user, Stock stock, Integer quantity, BigDecimal averageBuyPrice) {
        this.id = id;
        this.user = user;
        this.stock = stock;
        this.quantity = quantity;
        this.averageBuyPrice = averageBuyPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAverageBuyPrice() {
        return averageBuyPrice;
    }

    public void setAverageBuyPrice(BigDecimal averageBuyPrice) {
        this.averageBuyPrice = averageBuyPrice;
    }
}
