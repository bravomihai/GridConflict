package ui.setup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import model.GameState;
import ui.components.ItemRow;
import ui.components.MonsterRow;
import ui.core.SceneController;
import ui.game.GameController;


public class SetupController {

    @FXML
    public Button startGameButton;

    @FXML
    public Button backToMenuButton;

    @FXML
    private MapSetupController mapSetupController;

    @FXML
    private GameSetupController gameSetupController;

    @FXML
    private void initialize() {

        startGameButton.disableProperty().bind(
                mapSetupController.setupValidProperty().not()
        );

    }

    public void handleBackToMenu(ActionEvent actionEvent) {
        SceneController.switchTo("/ui/menu/Menu.fxml");
    }

    public void handleStartGame(ActionEvent e) throws Exception {

        GameState state = new GameState();

        state.H = Integer.parseInt(mapSetupController.mapHeightField.getText());
        state.W = Integer.parseInt(mapSetupController.mapWidthField.getText());

        state.players[0] = mapSetupController.playerRows.get(0).toPlayer();
        state.players[1] = mapSetupController.playerRows.get(1).toPlayer();

        for(ItemRow i : mapSetupController.itemRows){
            state.items.add(i.toItem());
        }

        for(MonsterRow m : mapSetupController.monsterRows){
            state.monsters.add(m.toMonster());
        }

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/ui/game/Game.fxml")
        );

        Parent root = loader.load();

        GameController controller = loader.getController();

        controller.initGame(state);

        Scene scene = new Scene(root);

        scene.getStylesheets().add(
                SceneController.class.getResource("/ui/style/dark.css").toExternalForm()
        );

        SceneController.getStage().setScene(scene);
    }
}