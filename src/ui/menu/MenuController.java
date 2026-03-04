package ui.menu;

import javafx.application.Platform;
import javafx.fxml.FXML;
import ui.core.SceneController;

public class MenuController {

    @FXML
    private void handleStart() {
        SceneController.switchTo("/ui/setup/Setup.fxml");
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }
}