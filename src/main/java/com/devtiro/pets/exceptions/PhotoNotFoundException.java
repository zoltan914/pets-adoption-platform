package com.devtiro.pets.exceptions;

public class PhotoNotFoundException extends RuntimeException {
    public PhotoNotFoundException(String message) {
        super(message);
    }
}
