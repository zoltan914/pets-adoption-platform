package com.devtiro.pets.exceptions;

public class ApplicationNotFoundException extends RuntimeException {
    public ApplicationNotFoundException(String message) {
        super(message);
    }
}
