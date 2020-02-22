package ua.restaurant.spring.exception;

import lombok.Getter;

@Getter
public class NotEnoughItemsException extends Exception {
    String message;

    public NotEnoughItemsException(String message) {
        this.message = message;
    }
}
