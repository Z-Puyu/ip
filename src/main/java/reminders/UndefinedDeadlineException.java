package reminders;

/**
 * Exception thrown when a deadline is undefined.
 */
public class UndefinedDeadlineException extends RuntimeException {
    public UndefinedDeadlineException(String message) {
        super(message);
    }
}
