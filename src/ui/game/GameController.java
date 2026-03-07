package ui.game;

import javafx.event.ActionEvent;
import ui.core.SceneController;

public class GameController {
    public void handleBackToSetup(ActionEvent actionEvent) { SceneController.switchTo("/ui/setup/Setup.fxml"); }
}
