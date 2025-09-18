package comments;

/**
 * The commenter for when a task is reset.
 */
public class SheogorathTaskResetCommenter implements Commenter {
    @Override
    public String commentOn(CommentContext context) {
        return "Time to return to the hum drum day-to-day.\n\t" + context.task();
    }
}
