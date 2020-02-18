package ua.restaurant.spring.exceptions;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class ItemNotFoundException extends Exception {
    private String message;

    public ItemNotFoundException(String message) {
        this.message = message;
    }
}
