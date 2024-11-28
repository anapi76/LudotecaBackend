package com.ccsw.tutorial.exceptions;

public class NameAlreadyExistsException extends RuntimeException {

    public NameAlreadyExistsException(String message) {
        super(message);
    }

}
