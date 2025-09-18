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
    /**
     * Creates a comment context from an input command.
     * @param command the input command
     * @return the comment context
     */
    public static CommentContext ofCommand(InputCommand command) {
        return new CommentContext(command, null, 0, null, null, 0);
    }

    /**
     * Creates a comment context from a task.
     * @param task the task
     * @param index the index of the task
     * @return the comment context
     */
    public static CommentContext ofTask(Task task, int index) {
        return new CommentContext(null, task, index, null, null, 0);
    }

    /**
     * Creates a comment context from a task list.
     * @param taskList the task list
     * @param lastChangedTask the last changed task
     * @param taskListSize the size of the task list
     * @return the comment context
     */
    public static CommentContext ofTaskList(Iterable<Task> taskList, Task lastChangedTask, int taskListSize) {
        return new CommentContext(null, null, 0, lastChangedTask, taskList, taskListSize);
    }
}
