package com.stocktrading.exception;

/**
 * Thrown when a user attempts to sell more shares of a stock
 * than they currently hold in their portfolio.
 */
public class InsufficientSharesException extends RuntimeException {

    public InsufficientSharesException(String message) {
        super(message);
    }
}
