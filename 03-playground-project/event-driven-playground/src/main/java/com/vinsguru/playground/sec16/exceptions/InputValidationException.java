package com.vinsguru.playground.sec16.exceptions;

public class InputValidationException extends RuntimeException {

    private static final String MESSAGE = "Invalid orderId: %d";

    public InputValidationException(Integer orderId) {
        super(MESSAGE.formatted(orderId));
    }

}
