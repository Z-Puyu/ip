import java.util.HashMap;
import java.util.Map;

public class InputMapping {
    private static InputMapping instance = new InputMapping();
    private final Map<String, InputAction> map = new HashMap<>();

    public static InputMapping getInstance() {
        if (InputMapping.instance == null) {
            InputMapping.instance = new InputMapping();
        }

        return InputMapping.instance;
    }

    public InputMapping map(String input, InputAction action) {
        this.map.put(input, action);
        return this;
    }

    public static Input interpret(String input) {
        return new Input(InputMapping.instance.map.getOrDefault(input, InputAction.EnterText), input);
    }
}
