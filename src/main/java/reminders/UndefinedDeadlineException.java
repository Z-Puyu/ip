package reminders;

public class UndefinedDeadlineException extends RuntimeException {
    public UndefinedDeadlineException(String message) {
        super(message);
    }
}
