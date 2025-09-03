package comments;

import inputs.InputCommand;
import reminders.Task;

/**
 * The context of a comment.
 */
public record CommentContext(
        InputCommand command,
        Task task,
        int index,
        Task lastChangedTask,
        Iterable<Task> taskList,
        int taskListSize
) {
    public static CommentContext ofCommand(InputCommand command) {
        return new CommentContext(command, null, 0, null, null, 0);
    }

    public static CommentContext ofTask(Task task, int index) {
        return new CommentContext(null, task, index, null, null, 0);
    }

    public static CommentContext ofTaskList(Iterable<Task> taskList, Task lastChangedTask, int taskListSize) {
        return new CommentContext(null, null, 0, lastChangedTask, taskList, taskListSize);
    }
}
