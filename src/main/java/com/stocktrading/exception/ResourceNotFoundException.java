package com.stocktrading.exception;

/**
 * Thrown when a requested resource (user, stock, etc.)
 * is not found in the database.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
