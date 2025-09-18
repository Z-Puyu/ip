package reminders;

/**
 * Exception thrown when a task is empty.
 */
public class EmptyTaskException extends RuntimeException {
    public EmptyTaskException(String message) {
        super(message);
    }
}
