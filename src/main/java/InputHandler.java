import java.util.*;
import java.util.function.Consumer;

public class InputHandler {
    private final Map<InputAction, Set<Consumer<String>>> listeners = new HashMap<>();

    public InputHandler addListener(InputAction action, Consumer<String> listener) {
        if (action == null || listener == null) {
            return this;
        }

        if (this.listeners.containsKey(action)) {
            this.listeners.get(action).add(listener);
        } else {
            Set<Consumer<String>> set = new HashSet<>();
            set.add(listener);
            this.listeners.put(action, set);
        }

        return this;
    }

    public InputHandler removeListener(InputAction action, Consumer<String> listener) {
        if (listener != null && action != null && this.listeners.containsKey(action)) {
            this.listeners.get(action).remove(listener);
        }

        return this;
    }

    public InputHandler clearListener(InputAction action) {
        if (action != null) {
            this.listeners.remove(action);
        }

        return this;
    }

    public void reset() {
        this.listeners.clear();
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        while (Application.isRunning()) {
            Input input = InputMapping.interpret(sc.nextLine());
            InputAction action = input.action();
            if (this.listeners.containsKey(action)) {
                String args = input.args();
                this.listeners.get(action).forEach(consumer -> consumer.accept(args));
            }
        }
    }
}

