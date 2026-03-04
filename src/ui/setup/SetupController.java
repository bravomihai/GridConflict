package ui.setup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import ui.util.UIHelpers;
import ui.components.ObjectRow;

import java.util.ArrayList;
import java.util.List;

public class SetupController {
    @FXML private VBox root;

    @FXML private Label titleLabel;

    @FXML private Spinner<Integer> mapHeightSpinner;
    @FXML private Spinner<Integer> mapWidthSpinner;

    @FXML private Spinner<Integer> playerHealthSpinner;
    @FXML private Spinner<Integer> playerAttackSpinner;
    @FXML private Spinner<Integer> playerDefenseSpinner;
    @FXML private Spinner<Integer> playerStaminaSpinner;

    @FXML private VBox objectContainer;
    @FXML private Button addButton;
    List<ObjectRow> objectRows = new ArrayList<>();



    @FXML
    private void initialize() {
        UIHelpers.configureSpinner(playerHealthSpinner, 1, 999, 100);
        UIHelpers.configureSpinner(playerAttackSpinner, 0, 999, 40);
        UIHelpers.configureSpinner(playerDefenseSpinner, 0, 999, 0);
        UIHelpers.configureSpinner(playerStaminaSpinner, 0, 999, 20);
        UIHelpers.configureSpinner(mapHeightSpinner, 2, 52, 7);
        UIHelpers.configureSpinner(mapWidthSpinner, 2, 99, 7);
    }

    public void handleAddObject(ActionEvent actionEvent) {
        if(objectRows.size() == 10){
            addButton.setDisable(true);
            return;
        }
        ObjectRow objectRow = new ObjectRow();
        objectRows.add(objectRow);

    }
}