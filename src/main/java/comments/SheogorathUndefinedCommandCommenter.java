package comments;

import inputs.InputCommand;

public class SheogorathUndefinedCommandCommenter implements Commenter<InputCommand> {
    @Override
    public String commentOn(InputCommand context) {
        return "";
    }
}
