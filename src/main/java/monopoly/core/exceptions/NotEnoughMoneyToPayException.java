package monopoly.core.exceptions;

public class NotEnoughMoneyToPayException extends Exception {
    public NotEnoughMoneyToPayException() {
        super("Not enough money to pay, player is out");
    }
    public NotEnoughMoneyToPayException(String message) {
        super(message);
    }
}
