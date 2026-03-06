package ui.setup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import ui.components.PlayerRow;
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

    @FXML private GridPane playerGrid;

    @FXML private GridPane objectGrid;
    @FXML private Button addObjectButton;
    List<ObjectRow> objectRows = new ArrayList<>();

    @FXML
    private void initialize() {
        //init map spinners
        UIHelpers.configureSpinner(mapHeightSpinner, 2, 52, 7);
        UIHelpers.configureSpinner(mapWidthSpinner, 2, 99, 7);

        //init player spinners
        playerGrid.add(new Label("Row"), 1, 0);
        playerGrid.add(new Label("Column"), 2, 0);
        playerGrid.add(new Label("Health"), 3, 0);
        playerGrid.add(new Label("Attack"), 4, 0);
        playerGrid.add(new Label("Defense"), 5, 0);
        playerGrid.add(new Label("Stamina"), 6, 0);

        PlayerRow  A = new PlayerRow();
        playerGrid.add(new Label("Player #1"), 0, 1);
        for (int i = 0; i < A.getSpinners().size(); i++) {
            playerGrid.add(A.getSpinners().get(i) , i + 1, 1);
        }

        PlayerRow  B = new PlayerRow();
        playerGrid.add(new Label("Player #2"), 0, 2);
        for (int i = 0; i < B.getSpinners().size(); i++) {
            playerGrid.add(B.getSpinners().get(i), i + 1, 2);
        }

        //init object spinners

        //init monster spinners
    }

    private void refreshObjectGrid(){
        objectGrid.getChildren().removeIf(node ->
                GridPane.getRowIndex(node) != null &&
                        GridPane.getRowIndex(node) > 0
        );

        if(objectRows.size() < 10){
            addObjectButton.setDisable(false);
        }

        if(objectRows.size() >= 10){
            addObjectButton.setDisable(true);
        }

        for (int r = 0; r < objectRows.size(); r++) {

            ObjectRow obj = objectRows.get(r);

            for (int c = 0; c < obj.getFields().size(); c++) {
                objectGrid.add(obj.getFields().get(c), c, r + 1);
            }

            objectGrid.add(obj.getRemoveButton(), 6, r + 1);
        }

    }

    public void handleAddObject(ActionEvent actionEvent) {

        ObjectRow objectRow = new ObjectRow();

        objectRow.getRemoveButton().setOnAction(e -> {
            objectRows.remove(objectRow);
            refreshObjectGrid();
        });

        objectRows.add(objectRow);
        refreshObjectGrid();
    }
}