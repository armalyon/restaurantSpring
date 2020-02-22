package ua.restaurant.spring.exception;

import lombok.Getter;

@Getter
public class RegexMismatchException extends Exception {
    private String field;

    public RegexMismatchException(String field) {
        this.field = field;
    }
}
