package comments;

import reminders.Task;

/**
 * The commenter for adding a task.
 */
public class SheogorathAddTaskCommenter implements Commenter {
    @Override
    public String commentOn(CommentContext context) {
        Task task = context.lastChangedTask();
        int size = context.taskListSize();
        return String.format("""
                %s? Oh, good choice. Well, good for me. Now go, before I change my mind.
                \t%s
                You seem to be having %d small problem%s... or perhaps it's a big problem?
                Maybe if you shrunk the whole thing down a little first?
                """, task.getDescription(), task, size, size > 1 ? "s" : ""
        );
    }
}
