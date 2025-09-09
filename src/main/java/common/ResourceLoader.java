package common;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

/**
 * A utility class for loading resources.
 */
public class ResourceLoader {
    /**
     * A record for holding a JavaFX node and its FXMLLoader.
     */
    public record FxmlResource<T extends Node>(T node, FXMLLoader loader) { }

    /**
     * Loads a resource by absolute path.
     * @param path the absolute path of the resource
     * @return the URL of the resource
     */
    public static URL load(String path) {
        return ResourceLoader.class.getResource(path);
    }

    /**
     * Loads a resource by absolute path as an input stream.
     * @param path the absolute path of the resource
     * @return the input stream of the resource
     */
    public static InputStream loadAsStream(String path) {
        return ResourceLoader.class.getResourceAsStream(path);
    }

    /**
     * Loads a JavaFX node by absolute path to its FXML file.
     * @param path the absolute path to an FXML file
     * @param <T> the type of the JavaFX node
     * @return the loaded JavaFX node
     */
    public static <T extends Node> FxmlResource<T> loadFxml(String path) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ResourceLoader.load(path));
            return new FxmlResource<>(fxmlLoader.load(), fxmlLoader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new FxmlResource<>(null, null);
    }

    /**
     * Loads a JavaFX node by absolute path to its FXML file.
     * @param path the absolute path to an FXML file
     * @param controller the controller for the FXML file
     * @param root the root for the FXML file
     * @param <T> the type of the JavaFX node
     */
    public static <T extends Node> void loadFxml(String path, T controller, T root) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ResourceLoader.load(path));
            fxmlLoader.setController(controller);
            fxmlLoader.setRoot(root);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
