package gui;

import common.ResourceLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialogue box.
 */
public class DialogueBox extends HBox {
    private static final String DIALOGUE_BOX_FXML_PATH = "/view/DialogueBox.fxml";
    private static final Image USER_IMG = new Image(ResourceLoader.loadAsStream("/images/DaUser.jpg"));
    private static final Image BOT_IMG = new Image(ResourceLoader.loadAsStream("/images/DaDuke.png"));

    @FXML
    private Label text;
    @FXML
    private ImageView image;

    /**
     * Creates a dialogue box.
     * @param text the text
     * @param image the image
     */
    private DialogueBox(String text, Image image, boolean isWarning) {
        ResourceLoader.loadFxml(DIALOGUE_BOX_FXML_PATH, this, this);
        this.text.setText(text);
        if (isWarning) {
            this.text.getStyleClass().add("warning-label");
        }

        if (image != null) {
            this.image.setImage(image);
        } else {
            // If image fails to load, hide the ImageView and adjust the layout
            this.image.setVisible(false);
            this.image.setManaged(false);
        }
    }

    private DialogueBox flip() {
        ObservableList<Node> children = FXCollections.observableArrayList(getChildren());
        FXCollections.reverse(children);
        getChildren().setAll(children);
        setAlignment(Pos.TOP_LEFT);
        this.getStyleClass().add("reply-label");
        return this;
    }

    public static DialogueBox forUser(String text) {
        return new DialogueBox(text, USER_IMG, false);
    }

    public static DialogueBox forBot(String text, boolean isWarning) {
        return new DialogueBox(text, BOT_IMG, isWarning).flip();
    }
}
