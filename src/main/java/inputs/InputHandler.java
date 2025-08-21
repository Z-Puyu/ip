package inputs;

import common.Application;

import java.util.*;
import java.util.function.Consumer;

public class InputHandler {
    private final Map<String, InputAction> actions = new HashMap<>();
    private final Map<InputAction, Set<Consumer<InputCommand>>> listeners = new HashMap<>();

    public InputHandler link(String command, InputAction action, Consumer<InputCommand> handler) {
        this.actions.put(command, action);
        return this.addListener(action, handler);
    }

    public InputHandler addListener(InputAction action, Consumer<InputCommand> listener) {
        if (action == null || listener == null) {
            return this;
        }

        if (this.listeners.containsKey(action)) {
            this.listeners.get(action).add(listener);
        } else {
            Set<Consumer<InputCommand>> set = new HashSet<>();
            set.add(listener);
            this.listeners.put(action, set);
        }

        return this;
    }

    public InputHandler removeListener(InputAction action, Consumer<InputCommand> listener) {
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

    private InputAction interpret(String command) {
        return this.actions.getOrDefault(command, InputAction.EnterText);
    }

    private void handle(String input) throws IllegalArgumentException {
        StringTokenizer st = new StringTokenizer(input, " ");
        String text = st.nextToken();
        InputAction action = this.interpret(text);
        InputCommand command = new InputCommand(action, input, st);
        if (this.listeners.containsKey(action)) {
            this.listeners.get(action).forEach(consumer -> consumer.accept(command));
        }
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        while (Application.isRunning()) {
            try {
                this.handle(sc.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                sc.close();
                System.exit(1);
            }
        }
    }
}

