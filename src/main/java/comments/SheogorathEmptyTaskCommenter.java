package comments;

public class SheogorathEmptyTaskCommenter implements Commenter {
    @Override
    public String commentOn(CommentContext context) {
        return "Task description! Now where did you leave my task description?";
    }
}
