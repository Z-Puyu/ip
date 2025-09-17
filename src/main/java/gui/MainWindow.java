package gui;

import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Represents the main window of the application.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollView;
    @FXML
    private VBox dialogueContainer;
    @FXML
    private TextField inputField;
    @FXML
    private Button sendButton;
    private Consumer<String> onInput;

    /**
     * Append an input handler.
     * @param onInput the input handler
     */
    public void onInput(Consumer<String> onInput) {
        if (this.onInput == null) {
            this.onInput = onInput;
        } else {
            this.onInput = this.onInput.andThen(onInput);
        }
    }

    @FXML
    public void initialize() {
        scrollView.vvalueProperty().bind(dialogueContainer.heightProperty());
    }

    /**
     * Creates a dialog box containing user input, and appends it to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String text = inputField.getText().trim();
        if (text.isBlank()) {
            return;
        }

        dialogueContainer.getChildren().add(DialogueBox.forUser(text));
        onInput.accept(text);
        inputField.clear();
    }

    public void present(String text, boolean isWarning) {
        dialogueContainer.getChildren().add(DialogueBox.forBot(text, isWarning));
    }
}
