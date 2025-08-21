package comments;

import reminders.MemoStateInfo;
import reminders.Task;

public class SheogorathAddTaskCommenter implements Commenter<MemoStateInfo> {
    @Override
    public String commentOn(MemoStateInfo context) {
        Task task = context.lastChangedTask();
        int size = context.memo().size();
        return String.format("""
                %s? Oh, good choice. Well, good for me. Now go, before I change my mind.
                \t%s
                You seem to be having %d small problem%s... or perhaps it's a big problem?
                Maybe if you shrunk the whole thing down a little first?
                """, task.getDescription(), task, size, size > 1 ? "s" : ""
        );
    }
}
