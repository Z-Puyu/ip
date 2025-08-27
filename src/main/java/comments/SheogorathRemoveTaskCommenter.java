package comments;

public class SheogorathRemoveTaskCommenter implements Commenter {
    @Override
    public String commentOn(CommentContext context) {
        return "Ah, wonderful, wonderful! Why waste all that time on that task when it can so easily be removed! ";
    }
}
