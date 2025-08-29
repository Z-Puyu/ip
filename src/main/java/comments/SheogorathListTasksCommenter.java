package comments;

import reminders.Task;

public class SheogorathListTasksCommenter implements Commenter {
    @Override
    public String commentOn(CommentContext context) {
        StringBuilder sb = new StringBuilder("Your mortal obligations await!\n");
        int index = 1;
        for (Task task : context.taskList()) {
            sb.append(String.format("\t%d.%s\n", index, task));
            index += 1;
        }

        return sb.toString();
    }
}
