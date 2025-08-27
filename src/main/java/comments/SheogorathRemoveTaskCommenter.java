package comments;

import reminders.TaskInfo;

public class SheogorathRemoveTaskCommenter implements Commenter<TaskInfo> {
    @Override
    public String commentOn(TaskInfo context) {
        return "Ah, wonderful, wonderful! Why waste all that time on that task when it can so easily be removed! ";
    }
}
