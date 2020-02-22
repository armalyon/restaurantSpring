package ua.restaurant.spring.domain.type;

public enum OrderStatement {
    WAITING("Waiting for confirmation", "Очікує на підтвердження"),
    CONFIRMED("Confirmed", "Підтверджено"),
    REJECTED("Rejected", "Відмовлено"),
    INVOICED("Invoiced", "Виставлено рахунок");

    private String message;
    private String messageUA;
    OrderStatement(String message, String messageUA) {
        this.message = message;
        this.messageUA = messageUA;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageUA() {
        return messageUA;
    }
}
