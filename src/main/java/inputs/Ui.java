package inputs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.function.Consumer;

import common.Application;

public class Ui {
    private final Parser parser = new Parser();
    private final Map<InputAction, Set<Consumer<InputCommand>>> listeners = new HashMap<>();

    public Ui link(String command, InputAction action, Consumer<InputCommand> handler) {
        parser.link(command, action);
        return addListener(action, handler);
    }

    public Ui addListener(InputAction action, Consumer<InputCommand> listener) {
        if (action == null || listener == null) {
            return this;
        }

        if (listeners.containsKey(action)) {
            listeners.get(action).add(listener);
        } else {
            Set<Consumer<InputCommand>> set = new HashSet<>();
            set.add(listener);
            listeners.put(action, set);
        }

        return this;
    }

    public Ui removeListener(InputAction action, Consumer<InputCommand> listener) {
        if (listener != null && action != null && listeners.containsKey(action)) {
            listeners.get(action).remove(listener);
        }

        return this;
    }

    public Ui clearListener(InputAction action) {
        if (action != null) {
            listeners.remove(action);
        }

        return this;
    }

    public void reset() {
        listeners.clear();
    }

    private void handle(String input) {
        StringTokenizer st = new StringTokenizer(input, " ");
        String text = st.nextToken();
        InputAction action = parser.interpret(text);
        InputCommand command = new InputCommand(action, input, st);
        if (listeners.containsKey(action)) {
            listeners.get(action).forEach(consumer -> consumer.accept(command));
        }
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        while (Application.isRunning()) {
            handle(sc.nextLine());
        }
    }
}

