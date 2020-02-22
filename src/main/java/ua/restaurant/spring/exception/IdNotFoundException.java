package ua.restaurant.spring.exception;

import lombok.Getter;

@Getter
public class IdNotFoundException extends Exception {
    private String message;
    private long id;

    public IdNotFoundException(String message, long id) {
        this.message = message;
        this.id = id;
    }
}
