-- Stock Trading Platform - MySQL Database Schema

CREATE DATABASE IF NOT EXISTS stock_trading_db;
USE stock_trading_db;

-- Table: users - Stores registered traders with their cash balance
CREATE TABLE IF NOT EXISTS users (
    id          BIGINT          NOT NULL AUTO_INCREMENT,
    username    VARCHAR(50)     NOT NULL,
    email       VARCHAR(100)    NOT NULL,
    balance     DECIMAL(15, 2)  NOT NULL DEFAULT 100000.00,
    created_at  TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username),
    UNIQUE KEY uk_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: stocks - Stores listed stocks with current and previous prices
CREATE TABLE IF NOT EXISTS stocks (
    id              BIGINT          NOT NULL AUTO_INCREMENT,
    symbol          VARCHAR(10)     NOT NULL,
    company_name    VARCHAR(100)    NOT NULL,
    current_price   DECIMAL(10, 2)  NOT NULL,
    previous_close  DECIMAL(10, 2),
    volume          BIGINT          DEFAULT 0,
    updated_at      TIMESTAMP       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_symbol (symbol)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: portfolios - Tracks each user's stock holdings and average buy price
CREATE TABLE IF NOT EXISTS portfolios (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    user_id             BIGINT          NOT NULL,
    stock_id            BIGINT          NOT NULL,
    quantity            INT             NOT NULL,
    average_buy_price   DECIMAL(10, 2)  NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_stock (user_id, stock_id),
    CONSTRAINT fk_portfolio_user  FOREIGN KEY (user_id)  REFERENCES users(id)  ON DELETE CASCADE,
    CONSTRAINT fk_portfolio_stock FOREIGN KEY (stock_id) REFERENCES stocks(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: transactions - Immutable audit log of every buy and sell operation
CREATE TABLE IF NOT EXISTS transactions (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    user_id             BIGINT          NOT NULL,
    stock_id            BIGINT          NOT NULL,
    type                ENUM('BUY', 'SELL') NOT NULL,
    quantity            INT             NOT NULL,
    price_per_share     DECIMAL(10, 2)  NOT NULL,
    total_amount        DECIMAL(15, 2)  NOT NULL,
    transaction_date    TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_transaction_user  FOREIGN KEY (user_id)  REFERENCES users(id)  ON DELETE CASCADE,
    CONSTRAINT fk_transaction_stock FOREIGN KEY (stock_id) REFERENCES stocks(id) ON DELETE CASCADE,
    INDEX idx_user_date (user_id, transaction_date DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
