package ui.util;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import util.IntRange;


public class UIHelpers {
    public static TextField configureTextField(IntRange range, int defaultValue) {

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

            if (value < range.getMin() || value > range.getMax()) {
                return null;
            }

            return change;
        });

        field.setTextFormatter(formatter);

        return field;
    }

    public static void configureTextField(TextField field, IntRange range, int defaultValue) {

        field.setText(String.valueOf(defaultValue));
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

            if (value < range.getMin() || value > range.getMax()) {
                return null;
            }

            return change;
        });

        field.setTextFormatter(formatter);
    }

}
