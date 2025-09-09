package comments;

/**
 * The commenter for invalid tasks.
 */
public class SheogorathDuplicateTaskCommenter implements Commenter {
    @Override
    public String commentOn(CommentContext context) {
        return String.format("""
                Ta-ta! A duplicate task:
                \t%s
                That's endless responsibilities you've got, huh? Are you mad? Or... me?""", context.task());
    }
}
