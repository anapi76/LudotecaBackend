package com.ccsw.tutorial.exceptions;

public class LoanConflictException extends RuntimeException {
    public LoanConflictException(String message) {
        super(message);
    }
}
