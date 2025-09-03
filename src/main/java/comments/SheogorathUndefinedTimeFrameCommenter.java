package comments;

/**
 * The commenter for undefined time frames.
 */
public class SheogorathUndefinedTimeFrameCommenter implements Commenter {
    @Override
    public String commentOn(CommentContext context) {
        return """
                When does this begin? And when does it end?
                Well? Spit it out, mortal. I haven't got an eternity!
                Actually... I do. Little joke.
                """;
    }
}
