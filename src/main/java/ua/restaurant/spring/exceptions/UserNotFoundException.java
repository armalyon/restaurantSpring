package ua.restaurant.spring.exceptions;

import lombok.Getter;

@Getter
public class UserNotFoundException extends Exception  {
    private String message;
    private String username;

    public UserNotFoundException(String message, String username) {
        this.message = message;
        this.username = username;
    }
}
