package com.castro.bluefood.application.sevice;

@SuppressWarnings("serial")
public class ValidationException extends Exception {

    public ValidationException(String message) {
        super(message);
    }
}
