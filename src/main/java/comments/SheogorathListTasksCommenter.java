package comments;

import reminders.MemoStateInfo;

public class SheogorathListTasksCommenter implements Commenter<MemoStateInfo> {
    @Override
    public String commentOn(MemoStateInfo context) {
        StringBuilder sb = new StringBuilder("Your mortal obligations await!\n");
        for (int i = 0; i < context.memo().size(); i += 1) {
            sb.append(String.format("\t%d.%s", i + 1, context.memo().get(i)));
            sb.append("\n");
        }

        return sb.toString();
    }
}
