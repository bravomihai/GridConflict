package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import ui.core.SceneController;

public class MainApp extends Application{

    @Override
    public void start(Stage stage) {
        SceneController.init(stage);
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ui/menu/Menu.fxml")
            );

            Parent root = loader.load();

            Scene scene = new Scene(root);
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

            double width = screenBounds.getWidth() * 0.8;
            double height = screenBounds.getHeight() * 0.8;

            stage.setWidth(width);
            stage.setHeight(height);
            stage.setMinWidth(width);
            stage.setMinHeight(height);
            stage.setScene(scene);
            stage.setTitle("GridConflict");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        launch();
    }

}
