package comments;

import inputs.InputCommand;
import reminders.Memo;
import reminders.Task;

public record CommentContext(InputCommand command, Task task, int index, Task lastChangedTask, Memo memo) {
    public static CommentContext OfCommand(InputCommand command)  {
        return new CommentContext(command, null, 0, null, null);
    }

    public static CommentContext OfTask(Task task, int index) {
        return new CommentContext(null, task, index, null, null);
    }

    public static CommentContext OfMemo(Memo memo, Task lastChangedTask) {
        return new CommentContext(null, null, 0, lastChangedTask, memo);
    }
}
