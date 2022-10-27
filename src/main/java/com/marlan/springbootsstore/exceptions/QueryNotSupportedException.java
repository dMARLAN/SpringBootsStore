package com.marlan.springbootsstore.exceptions;

public class QueryNotSupportedException extends RuntimeException {
    public QueryNotSupportedException(String message) {
        super(message);
    }
}
