package comments;

public class SheogorathListTasksCommenter implements Commenter {
    @Override
    public String commentOn(CommentContext context) {
        StringBuilder sb = new StringBuilder("Your mortal obligations await!\n");
        for (int i = 0; i < context.taskList().size(); i += 1) {
            sb.append(String.format("\t%d.%s", i + 1, context.taskList().get(i)));
            sb.append("\n");
        }

        return sb.toString();
    }
}
