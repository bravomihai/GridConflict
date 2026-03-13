package ui.setup;

import content.PresetGameStates;
import enginebridge.NativeEngine;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import model.GameState;
import ui.core.SceneController;
import ui.game.GameController;


import java.net.URL;


public class SetupController {

    @FXML
    private MapSetupController mapSetupController;

    @FXML
    private GameSetupController gameSetupController;

    @FXML public Button startGameButton;

    @FXML public Button backToMenuButton;

    private GameState gameState = new GameState();

    @FXML
    private void initialize() {

        //ONLY FOR TESTING
        PresetGameStates presetGameStates = new PresetGameStates();
        gameState = presetGameStates.get(0);


        //map setup (upper part of setup page)
        mapSetupController.setGameState(gameState);
        mapSetupController.setSceneWidth(SceneController.getStage().getWidth());
        mapSetupController.resize();
        mapSetupController.setOnSetupChanged(gameSetupController::computeBalance);

        //game setup (lower part of setup page)
        gameSetupController.setGameState(gameState);
        gameSetupController.setSceneWidth(SceneController.getStage().getWidth());
        gameSetupController.resize();
        gameSetupController.difficultySlider.valueProperty().addListener((_, _, newVal) -> {
            gameSetupController.computeBalance();
        });
        gameSetupController.computeBalance();


        SceneController.getStage().widthProperty().addListener((_, _, newV) ->{
                mapSetupController.setSceneWidth(newV.doubleValue());
                mapSetupController.resize();
                gameSetupController.setSceneWidth(newV.doubleValue());
                gameSetupController.resize();
            }
        );

        startGameButton.disableProperty().bind(
                gameState.validProperty().not()
        );

    }


    public void handleBackToMenu() {
        SceneController.switchTo("/ui/menu/Menu.fxml");
    }

    public void handleStartGame() throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/ui/game/Game.fxml")
        );

        Parent root = loader.load();

        GameController controller = loader.getController();

        controller.initGame(gameState);

        Scene scene = new Scene(root);

        URL css = SceneController.class.getResource("/ui/style/dark.css");
        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        }

        SceneController.getStage().setScene(scene);
    }
}