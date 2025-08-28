package inputs;

import java.util.HashMap;
import java.util.Map;

/**
 * A parser that can interpret a string into an {@link InputAction}.
 */
public class Parser {
    private final Map<String, InputAction> actions = new HashMap<>();

    /**
     * Interprets the given command into an {@link InputAction}.
     * @param command the command to interpret
     * @return the interpreted action
     */
    public InputAction interpret(String command) {
        return this.actions.getOrDefault(command, InputAction.Undefined);
    }

    /**
     * Links a command to an {@link InputAction}.
     * @param command the command to link
     * @param action the action to link to
     */
    public void link(String command, InputAction action) {
        this.actions.put(command, action);
    }
}
