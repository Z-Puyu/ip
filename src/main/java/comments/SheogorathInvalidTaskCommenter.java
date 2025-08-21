package comments;

import reminders.TaskInfo;

public class SheogorathInvalidTaskCommenter implements Commenter<TaskInfo> {
    @Override
    public String commentOn(TaskInfo context) {
        return "Now that's the real question, isn't it? Because honestly, that task doesn't exist! Ha!";
    }
}
