package reminders;

/**
 * Exception thrown when a time frame is undefined.
 */
public class UndefinedTimeFrameException extends RuntimeException {
    public UndefinedTimeFrameException(String message) {
        super(message);
    }
}
