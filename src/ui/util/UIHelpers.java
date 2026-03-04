package ui.util;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class UIHelpers {
    //helper method, i1 = lower bound, i2 = upper bound, i3 = start value
    static public void configureSpinner(Spinner<Integer> spinner, int i1, int i2, int i3) {
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
}
