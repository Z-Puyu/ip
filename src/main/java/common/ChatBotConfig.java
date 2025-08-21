package common;

import comments.Commenter;
import reminders.MemoStateInfo;
import reminders.Task;
import reminders.TaskInfo;

import java.util.function.Function;

public record ChatBotConfig(
        String name,
        String logo,
        String greeting,
        String farewell,
        Commenter<TaskInfo> invalidTaskCommenter,
        Commenter<MemoStateInfo> addTaskCommenter,
        Commenter<MemoStateInfo> listTasksCommenter,
        Commenter<TaskInfo> markTaskCommenter,
        Commenter<TaskInfo> unmarkTaskCommenter
) { }
