package comments;

public class SheogorathUndefinedDeadlineCommenter implements Commenter {
    @Override
    public String commentOn(CommentContext context) {
        return """
                By when you want it done?
                Well? Spit it out, mortal. I haven't got an eternity!
                Actually... I do. Little joke.
                """;
    }
}
