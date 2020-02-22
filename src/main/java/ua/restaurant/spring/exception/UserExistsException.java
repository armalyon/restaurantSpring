package ua.restaurant.spring.exception;


import lombok.Getter;

@Getter
public class UserExistsException extends Exception {
    private String message;
    private String login;

    public UserExistsException(String message, String login) {
        this.message = message;
        this.login = login;
    }
}
