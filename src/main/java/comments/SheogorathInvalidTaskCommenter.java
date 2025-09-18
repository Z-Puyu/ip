package comments;

/**
 * The commenter for invalid tasks.
 */
public class SheogorathInvalidTaskCommenter implements Commenter {
    @Override
    public String commentOn(CommentContext context) {
        return "Now that's the real question, isn't it? Because honestly, that task doesn't exist! Ha!";
    }
}
