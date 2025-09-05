package gui;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialogue box.
 */
public class DialogueBox extends HBox {

    private Label text;
    private ImageView image;

    /**
     * Creates a dialogue box.
     * @param text the text
     * @param image the image
     */
    public DialogueBox(String text, Image image) {
        this.text = new Label(text);
        this.image = new ImageView(image);
        this.getChildren().addAll(this.text, this.image);
    }
}
