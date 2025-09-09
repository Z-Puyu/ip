package inputs;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.function.Consumer;

import common.App;
import common.ResourceLoader;
import gui.MainWindow;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A UI that can interpret user input.
 */
public class Ui {
    private static final String MAIN_FXML_PATH = "/view/MainWindow.fxml";

    private MainWindow mainWindow;
    private final Parser parser = new Parser();
    private final Map<InputAction, Set<Consumer<InputCommand>>> listeners = new HashMap<>();

    /**
     * Registers a callback for a specific user-input command.
     *
     * @param command the command to link
     * @param action  the action to link to
     * @return this UI
     */
    public Ui link(String command, InputAction action, Consumer<InputCommand> handler) {
        parser.link(command, action);
        return addListener(action, handler);
    }

    /**
     * Adds a listener for a specific input action.
     *
     * @param action   the action to listen to
     * @param listener the listener to add
     * @return this UI
     */
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

    /**
     * Removes a listener for a specific input action.
     *
     * @param action   the action to remove the listener from
     * @param listener the listener to remove
     * @return this UI
     */
    public Ui removeListener(InputAction action, Consumer<InputCommand> listener) {
        if (listener != null && action != null && listeners.containsKey(action)) {
            listeners.get(action).remove(listener);
        }

        return this;
    }

    /**
     * Removes all listeners for a specific input action.
     *
     * @param action the action to remove the listener from
     * @return this UI
     */
    public Ui clearListener(InputAction action) {
        if (action != null) {
            listeners.remove(action);
        }

        return this;
    }

    /**
     * Clears all listeners.
     */
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

    /**
     * Runs the UI. This means that it will listen for user input and call the registered listeners.
     */
    public void run() {
        assert App.isRunning();
        Scanner sc = new Scanner(System.in);
        while (App.isRunning()) {
            handle(sc.nextLine());
        }
    }

    /**
     * Starts the UI.
     *
     * @param stage the stage to use
     */
    public void start(Stage stage) {
        assert stage != null && App.isRunning();
        ResourceLoader.FxmlResource<AnchorPane> resource = ResourceLoader.loadFxml(MAIN_FXML_PATH);
        stage.setScene(new Scene(resource.node()));
        mainWindow = resource.loader().getController();
        mainWindow.onInput(this::handle);
        stage.show();
    }

    public void present(String text) {
        mainWindow.present(text);
    }
}

