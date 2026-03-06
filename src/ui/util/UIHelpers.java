package ui.util;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

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

    public static TextField configureTextField(int min, int max, int defaultValue) {

        TextField field = new TextField(String.valueOf(defaultValue));
        field.setPrefWidth(55);

        TextFormatter<Integer> formatter = new TextFormatter<>(change -> {

            String newText = change.getControlNewText();

            if (newText.isEmpty()) {
                return change;
            }

            if (!newText.matches("\\d+")) {
                return null;
            }

            int value = Integer.parseInt(newText);

            if (value < min || value > max) {
                return null;
            }

            return change;
        });

        field.setTextFormatter(formatter);

        return field;
    }
}
