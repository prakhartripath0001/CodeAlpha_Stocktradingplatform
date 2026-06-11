package com.stocktrading.exception;

/**
 * Thrown when a user attempts to buy shares but does not
 * have enough cash balance to cover the order.
 */
public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(String message) {
        super(message);
    }
}
