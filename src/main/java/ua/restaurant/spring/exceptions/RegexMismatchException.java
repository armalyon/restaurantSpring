package ua.restaurant.spring.exceptions;

import lombok.Getter;

@Getter
public class RegexMismatchException extends Exception {
    private String field;

    public RegexMismatchException(String field) {
        this.field = field;
    }
}
