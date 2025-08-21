package comments;

import inputs.InputCommand;

public class SheogorathUndefinedCommandCommenter implements Commenter<InputCommand> {
    @Override
    public String commentOn(InputCommand context) {
        return String.format("""
                %s? Reeaaaalllllyyyy? Ooh, ooh, what kind of command was that? A song? A summons?
                Wait, I know! A death threat written on the back of an Argonian concubine! Those are my favorite. 
                
                But seriously. What's the command you wanted to say?
                """, context.text().substring(0, context.text().indexOf(' ')));
    }
}
