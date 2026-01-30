package com.devtiro.pets.exceptions;

public class UserAccountDisabledException extends RuntimeException {
    public UserAccountDisabledException(String message) {
        super(message);
    }
}
