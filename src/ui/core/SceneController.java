package ui.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {
    private static Stage primaryStage;

    public static void init(Stage stage){
        primaryStage = stage;
    }

    public static void switchScene(Scene scene) {
        primaryStage.setScene(scene);
    }

    public static void switchTo(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    SceneController.class.getResource(fxmlPath)
            );

            Parent root = loader.load();
            Scene scene = new Scene(root);

            //!!TO BE REMOVED
            scene.getStylesheets().add(
                    SceneController.class.getResource("/ui/style/dark.css").toExternalForm()
            );

            primaryStage.setScene(scene);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load FXML: " + fxmlPath, e);
        }
    }

}
