package common;

import comments.Commenter;
import inputs.InputCommand;
import reminders.MemoStateInfo;
import reminders.TaskInfo;

public record ChatBotConfig(
        String name,
        String logo,
        String greeting,
        String farewell,
        Commenter<TaskInfo> invalidTaskCommenter,
        Commenter<MemoStateInfo> addTaskCommenter,
        Commenter<MemoStateInfo> listTasksCommenter,
        Commenter<TaskInfo> markTaskCommenter,
        Commenter<TaskInfo> unmarkTaskCommenter,
        Commenter<InputCommand> undefinedCommandCommenter,
        Commenter<TaskInfo> removeTaskCommenter,
        String missingTaskDescriptionComment,
        String missingDeadlineComment,
        String missingEventTimeComment
) { }
