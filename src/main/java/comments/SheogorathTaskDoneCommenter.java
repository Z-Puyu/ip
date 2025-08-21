package comments;

import reminders.Task;
import reminders.TaskInfo;

public class SheogorathTaskDoneCommenter implements Commenter<TaskInfo> {
    @Override
    public String commentOn(TaskInfo context) {
        Task task = context.task();
        return String.format("""
                %s? Check.
                \t%s
                Oh, I am so happy for you! My, what a burden to have carried. But you've done it!
                """, task.getDescription(), task
        );
    }
}
