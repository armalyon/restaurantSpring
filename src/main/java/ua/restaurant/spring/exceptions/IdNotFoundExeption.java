package ua.restaurant.spring.exceptions;

import lombok.Getter;

@Getter
public class IdNotFoundExeption extends Exception {
    private String message;
    private long id;

    public IdNotFoundExeption(String message, long id) {
        this.message = message;
        this.id = id;
    }
}
