package ua.restaurant.spring.exceptions;

import lombok.Getter;

@Getter
public class NotEnoughFundsException extends Exception {
    private String message;
    private long billId;
    private long foundsDifference;

    public NotEnoughFundsException(String message, long foundsDifference, long billId) {
        this.message = message;
        this.foundsDifference = foundsDifference;
        this.billId = billId;
    }
}
