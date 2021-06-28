package com.maersk.shoppingcart.exception;

public class OutOfQuantityException extends RuntimeException {
    public OutOfQuantityException(String message) {
        super(message);
    }
}
