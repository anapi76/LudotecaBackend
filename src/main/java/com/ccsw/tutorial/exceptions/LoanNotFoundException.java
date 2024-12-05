package com.ccsw.tutorial.exceptions;

public class LoanNotFoundException extends RuntimeException {

    public LoanNotFoundException(String message) {
        super(message);
    }

}
