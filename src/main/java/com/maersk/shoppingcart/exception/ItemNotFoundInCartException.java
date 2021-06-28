package com.maersk.shoppingcart.exception;

public class ItemNotFoundInCartException extends RuntimeException {
    public ItemNotFoundInCartException(String message) {
        super(message);
    }
}
