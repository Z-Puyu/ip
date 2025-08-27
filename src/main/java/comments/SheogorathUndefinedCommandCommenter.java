package comments;

import inputs.InputCommand;

public class SheogorathUndefinedCommandCommenter implements Commenter {
    @Override
    public String commentOn(CommentContext context) {
        String command = context.command().text();
        return String.format("""
                %s? Reeaaaalllllyyyy? Ooh, ooh, what kind of command was that? A song? A summons?
                Wait, I know! A death threat written on the back of an Argonian concubine! Those are my favorite. 
                
                But seriously. What's the command you wanted to say?
                """, command.substring(0, command.indexOf(' ')));
    }
}
