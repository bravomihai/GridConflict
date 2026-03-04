package ui.setup;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.geometry.Insets;

public class SetupController {
    @FXML
    private VBox root;

    @FXML
    private Label titleLabel;

    @FXML private Spinner<Integer> playerHealthSpinner;
    @FXML private Spinner<Integer> playerAttackSpinner;
    @FXML private Spinner<Integer> playerDefenseSpinner;
    @FXML private Spinner<Integer> playerStaminaSpinner;
    @FXML private Spinner<Integer> mapHeightSpinner;
    @FXML private Spinner<Integer> mapWidthSpinner;

    //helper method
    private void configureSpinner(Spinner<Integer> spinner, int i1, int i2, int i3) {
        spinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(i1, i2, i3)
        );
        spinner.setEditable(true);
        spinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                spinner.getEditor().setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    @FXML
    private void initialize() {
        configureSpinner(playerHealthSpinner, 1, 999, 100);
        configureSpinner(playerAttackSpinner, 0, 999, 40);
        configureSpinner(playerDefenseSpinner, 0, 999, 0);
        configureSpinner(playerStaminaSpinner, 0, 999, 20);
        configureSpinner(mapHeightSpinner, 2, 52, 7);
        configureSpinner(mapWidthSpinner, 2, 99, 7);
    }
}