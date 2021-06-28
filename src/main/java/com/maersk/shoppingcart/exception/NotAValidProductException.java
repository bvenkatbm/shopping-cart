package com.maersk.shoppingcart.exception;

public class NotAValidProductException extends RuntimeException {
    public NotAValidProductException(String message) {
        super(message);
    }
}
