# Stock Trading Platform

A simulated stock trading platform built with **Spring Boot** and **MySQL** that provides a REST API for user registration, real-time market data display, buy/sell operations, portfolio tracking, and transaction history.

---

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Database Schema](#database-schema)
- [Prerequisites](#prerequisites)
- [Setup and Installation](#setup-and-installation)
- [API Reference](#api-reference)
- [Usage Examples](#usage-examples)
- [Project Structure](#project-structure)

---

## Features

- **User Management** -- Register users who start with a virtual $100,000 cash balance.
- **Market Data Display** -- View all listed stocks with current prices, previous close, and volume.
- **Buy/Sell Operations** -- Execute buy and sell orders with real-time price execution, balance validation, and portfolio updates.
- **Portfolio Tracking** -- View holdings with per-stock and total gain/loss calculations (absolute and percentage).
- **Transaction History** -- Complete audit trail of all trades ordered by most recent.
- **Market Simulation** -- Stock prices fluctuate automatically every 30 seconds using a random walk algorithm.
- **Data Seeding** -- 10 pre-loaded stocks (AAPL, GOOGL, MSFT, AMZN, TSLA, META, NVDA, NFLX, JPM, V) on first startup.
- **Input Validation** -- Request body validation with descriptive error messages.
- **Global Exception Handling** -- Consistent JSON error responses across all endpoints.

---

## Tech Stack

| Component      | Technology                    |
|----------------|-------------------------------|
| Language       | Java 17                       |
| Framework      | Spring Boot 3.2               |
| ORM            | Spring Data JPA (Hibernate)   |
| Database       | MySQL 8                       |
| Build Tool     | Apache Maven                  |
| Code Reduction | Lombok                        |
| Validation     | Jakarta Bean Validation       |

---

## Architecture

The application follows a standard layered architecture:

```
Controller Layer    --> Handles HTTP requests and responses
       |
Service Layer       --> Contains business logic (trading engine, portfolio calculations)
       |
Repository Layer    --> JPA repositories for database access
       |
Entity Layer        --> JPA entities mapped to MySQL tables
```

Key design decisions:
- **OOP principles** are applied throughout: entities model the domain (User, Stock, Portfolio, Transaction), services encapsulate behavior, and DTOs separate API contracts from persistence models.
- **Transactional integrity** ensures that buy/sell operations either fully complete (balance update + portfolio update + transaction record) or fully roll back.
- **Market simulation** runs as a scheduled background task independent of API calls.

---

## Database Schema

The application uses four MySQL tables. The full DDL script is available in `schema.sql`.

```
+------------------+       +------------------+
|      users       |       |     stocks       |
+------------------+       +------------------+
| id (PK)          |       | id (PK)          |
| username (UQ)    |       | symbol (UQ)      |
| email (UQ)       |       | company_name     |
| balance          |       | current_price    |
| created_at       |       | previous_close   |
+------------------+       | volume           |
        |                  | updated_at       |
        |                  +------------------+
        |                          |
        v                          v
+-------------------+     +-------------------+
|   portfolios      |     |   transactions    |
+-------------------+     +-------------------+
| id (PK)           |     | id (PK)           |
| user_id (FK)      |     | user_id (FK)      |
| stock_id (FK)     |     | stock_id (FK)     |
| quantity          |     | type (BUY/SELL)   |
| average_buy_price |     | quantity          |
| UQ(user,stock)    |     | price_per_share   |
+-------------------+     | total_amount      |
                           | transaction_date  |
                           +-------------------+
```

---

## Prerequisites

1. **Java 17** or later -- [Download](https://adoptium.net/)
2. **Apache Maven 3.8+** -- [Download](https://maven.apache.org/download.cgi)
3. **MySQL 8.0+** -- [Download](https://dev.mysql.com/downloads/mysql/)

---

## Setup and Installation

### 1. Clone the Repository

```bash
git clone <repository-url>
cd CodeAlpha_Stocktradingplatform
```

### 2. Create the MySQL Database

Option A -- Let Hibernate create tables automatically (default, no action needed).

Option B -- Run the schema script manually:

```bash
mysql -u root -p < schema.sql
```

### 3. Configure Database Credentials

Edit `src/main/resources/application.properties` if your MySQL credentials differ from the defaults:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/stock_trading_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=root
```

### 4. Build the Project

```bash
mvn clean install
```

### 5. Run the Application

```bash
mvn spring-boot:run
```

The server starts at `http://localhost:8080`. On first startup, 10 stocks are automatically seeded into the database.

---

## API Reference

### User Endpoints

| Method | URL                     | Description                |
|--------|-------------------------|----------------------------|
| POST   | `/api/users`            | Register a new user        |
| GET    | `/api/users`            | List all users             |
| GET    | `/api/users/{username}` | Get user by username       |

### Stock Endpoints

| Method | URL                     | Description                |
|--------|-------------------------|----------------------------|
| GET    | `/api/stocks`           | List all stocks            |
| GET    | `/api/stocks/{symbol}`  | Get stock by symbol        |
| POST   | `/api/stocks`           | Add a new stock            |

### Trade Endpoints

| Method | URL                     | Description                |
|--------|-------------------------|----------------------------|
| POST   | `/api/trades/buy`       | Buy shares                 |
| POST   | `/api/trades/sell`      | Sell shares                |

### Portfolio Endpoint

| Method | URL                           | Description                        |
|--------|-------------------------------|------------------------------------|
| GET    | `/api/portfolio/{username}`   | View portfolio with gain/loss      |

### Transaction Endpoint

| Method | URL                              | Description                     |
|--------|----------------------------------|---------------------------------|
| GET    | `/api/transactions/{username}`   | View transaction history        |

---

## Usage Examples

All examples use `curl`. Replace values as needed.

### Register a User

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"username": "john_doe", "email": "john@example.com"}'
```

Response:
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "balance": 100000.00,
  "createdAt": "2026-06-12T01:30:00"
}
```

### View All Stocks

```bash
curl http://localhost:8080/api/stocks
```

Response:
```json
[
  {
    "id": 1,
    "symbol": "AAPL",
    "companyName": "Apple Inc.",
    "currentPrice": 189.84,
    "previousClose": 189.84,
    "volume": 0,
    "updatedAt": "2026-06-12T01:30:00"
  }
]
```

### Buy Shares

```bash
curl -X POST http://localhost:8080/api/trades/buy \
  -H "Content-Type: application/json" \
  -d '{"username": "john_doe", "stockSymbol": "AAPL", "quantity": 10}'
```

Response:
```json
{
  "message": "Successfully bought 10 shares of AAPL",
  "type": "BUY",
  "username": "john_doe",
  "stockSymbol": "AAPL",
  "companyName": "Apple Inc.",
  "quantity": 10,
  "pricePerShare": 189.84,
  "totalAmount": 1898.40,
  "remainingBalance": 98101.60,
  "transactionDate": "2026-06-12T01:31:00"
}
```

### Sell Shares

```bash
curl -X POST http://localhost:8080/api/trades/sell \
  -H "Content-Type: application/json" \
  -d '{"username": "john_doe", "stockSymbol": "AAPL", "quantity": 5}'
```

### View Portfolio

```bash
curl http://localhost:8080/api/portfolio/john_doe
```

Response:
```json
{
  "username": "john_doe",
  "cashBalance": 99050.40,
  "totalInvested": 949.20,
  "currentPortfolioValue": 952.10,
  "totalGainLoss": 2.90,
  "totalGainLossPercentage": 0.31,
  "holdings": [
    {
      "stockSymbol": "AAPL",
      "companyName": "Apple Inc.",
      "quantity": 5,
      "averageBuyPrice": 189.84,
      "currentPrice": 190.42,
      "investedValue": 949.20,
      "currentValue": 952.10,
      "gainLoss": 2.90,
      "gainLossPercentage": 0.31
    }
  ]
}
```

### View Transaction History

```bash
curl http://localhost:8080/api/transactions/john_doe
```

Response:
```json
[
  {
    "id": 2,
    "type": "SELL",
    "stockSymbol": "AAPL",
    "companyName": "Apple Inc.",
    "quantity": 5,
    "pricePerShare": 190.42,
    "totalAmount": 952.10,
    "transactionDate": "2026-06-12T01:32:00"
  },
  {
    "id": 1,
    "type": "BUY",
    "stockSymbol": "AAPL",
    "companyName": "Apple Inc.",
    "quantity": 10,
    "pricePerShare": 189.84,
    "totalAmount": 1898.40,
    "transactionDate": "2026-06-12T01:31:00"
  }
]
```

---

## Project Structure

```
CodeAlpha_Stocktradingplatform/
|-- pom.xml                         # Maven build configuration
|-- schema.sql                      # MySQL DDL schema script
|-- README.md                       # This file
|-- src/
    |-- main/
        |-- java/com/stocktrading/
        |   |-- StockTradingApplication.java    # Main entry point
        |   |-- model/
        |   |   |-- User.java                   # User entity
        |   |   |-- Stock.java                  # Stock entity
        |   |   |-- Portfolio.java              # Portfolio entity
        |   |   |-- Transaction.java            # Transaction entity
        |   |   |-- TransactionType.java        # BUY/SELL enum
        |   |-- repository/
        |   |   |-- UserRepository.java         # User data access
        |   |   |-- StockRepository.java        # Stock data access
        |   |   |-- PortfolioRepository.java    # Portfolio data access
        |   |   |-- TransactionRepository.java  # Transaction data access
        |   |-- dto/
        |   |   |-- TradeRequest.java           # Buy/sell request body
        |   |   |-- TradeResponse.java          # Trade confirmation response
        |   |   |-- PortfolioResponse.java      # Portfolio with performance
        |   |-- service/
        |   |   |-- UserService.java            # User business logic
        |   |   |-- StockService.java           # Stock business logic
        |   |   |-- TradeService.java           # Trading engine
        |   |   |-- PortfolioService.java       # Portfolio calculations
        |   |   |-- MarketSimulator.java        # Scheduled price simulator
        |   |-- controller/
        |   |   |-- UserController.java         # User REST endpoints
        |   |   |-- StockController.java        # Stock REST endpoints
        |   |   |-- TradeController.java        # Trade REST endpoints
        |   |   |-- PortfolioController.java    # Portfolio REST endpoint
        |   |   |-- TransactionController.java  # Transaction REST endpoint
        |   |-- exception/
        |   |   |-- GlobalExceptionHandler.java         # Centralized error handling
        |   |   |-- InsufficientFundsException.java     # Not enough cash
        |   |   |-- InsufficientSharesException.java    # Not enough shares
        |   |   |-- ResourceNotFoundException.java      # Entity not found
        |   |-- config/
        |       |-- DataSeeder.java             # Initial stock data loader
        |-- resources/
            |-- application.properties          # Application configuration
```
