package comments;

import reminders.TaskInfo;

public class SheogorathTaskResetCommenter implements Commenter<TaskInfo> {
    @Override
    public String commentOn(TaskInfo context) {
        return "Time to return to the hum drum day-to-day.\n\t" + context.task();
    }
}
