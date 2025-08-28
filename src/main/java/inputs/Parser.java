package inputs;

import java.util.HashMap;
import java.util.Map;

public class Parser {
    private final Map<String, InputAction> actions = new HashMap<>();

    public InputAction interpret(String command) {
        return this.actions.getOrDefault(command, InputAction.Undefined);
    }

    public void link(String command, InputAction action) {
        this.actions.put(command, action);
    }
}
