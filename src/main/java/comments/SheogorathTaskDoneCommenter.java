package comments;

import reminders.Task;

/**
 * The commenter for when a task is marked as done.
 */
public class SheogorathTaskDoneCommenter implements Commenter {
    @Override
    public String commentOn(CommentContext context) {
        Task task = context.task();
        return String.format("""
                %s? Check.
                \t%s
                Oh, I am so happy for you! My, what a burden to have carried. But you've done it!
                """, task.getDescription(), task
        );
    }
}
