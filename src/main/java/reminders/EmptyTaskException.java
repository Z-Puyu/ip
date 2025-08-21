package reminders;

public class EmptyTaskException extends RuntimeException {
    public EmptyTaskException(String message) {
        super(message);
    }
}
