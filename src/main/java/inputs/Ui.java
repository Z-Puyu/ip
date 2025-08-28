package inputs;

import common.Application;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.function.Consumer;

public class Ui {
    private final Parser parser = new Parser();
    private final Map<InputAction, Set<Consumer<InputCommand>>> listeners = new HashMap<>();

    public Ui link(String command, InputAction action, Consumer<InputCommand> handler) {
        this.parser.link(command, action);
        return this.addListener(action, handler);
    }

    public Ui addListener(InputAction action, Consumer<InputCommand> listener) {
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

    public Ui removeListener(InputAction action, Consumer<InputCommand> listener) {
        if (listener != null && action != null && this.listeners.containsKey(action)) {
            this.listeners.get(action).remove(listener);
        }

        return this;
    }

    public Ui clearListener(InputAction action) {
        if (action != null) {
            this.listeners.remove(action);
        }

        return this;
    }

    public void reset() {
        this.listeners.clear();
    }

    private void handle(String input) {
        StringTokenizer st = new StringTokenizer(input, " ");
        String text = st.nextToken();
        InputAction action = this.parser.interpret(text);
        InputCommand command = new InputCommand(action, input, st);
        if (this.listeners.containsKey(action)) {
            this.listeners.get(action).forEach(consumer -> consumer.accept(command));
        }
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        while (Application.isRunning()) {
            this.handle(sc.nextLine());
        }
    }
}

