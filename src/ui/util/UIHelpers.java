package ui.util;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import util.IntRange;

import static ui.util.UIAnimations.pulsateErrorBorder;


public class UIHelpers {
    public static TextField configureTextField(IntRange range, int defaultValue, Runnable validator) {

        TextField field = new TextField(String.valueOf(defaultValue));
        field.setPrefWidth(55);

        TextFormatter<Integer> formatter = new TextFormatter<>(change -> {

            String newText = change.getControlNewText();

            if (newText.isEmpty()) {
                return change;
            }

            if (!newText.matches("\\d+")) {
                pulsateErrorBorder(field, validator);
                return null;
            }

            int value = Integer.parseInt(newText);

            if (value < range.getMin() || value > range.getMax()) {
                pulsateErrorBorder(field, validator);
            }

            int clamped = Math.max(range.getMin(), Math.min(value, range.getMax()));

            if (clamped != value) {
                change.setRange(0, change.getControlText().length());
                change.setText(String.valueOf(clamped));
            }

            return change;
        });

        field.setTextFormatter(formatter);

        return field;
    }

    public static void configureTextField(TextField field, IntRange range, int defaultValue, Runnable validator) {

        field.setText(String.valueOf(defaultValue));
        field.setPrefWidth(55);

        TextFormatter<Integer> formatter = new TextFormatter<>(change -> {

            String newText = change.getControlNewText();

            if (newText.isEmpty()) {
                return change;
            }

            if (!newText.matches("\\d+")) {
                pulsateErrorBorder(field, validator);
                return null;
            }

            int value = Integer.parseInt(newText);

            if (value < range.getMin() || value > range.getMax()) {
                pulsateErrorBorder(field, validator);
            }

            int clamped = Math.max(range.getMin(), Math.min(value, range.getMax()));

            if (clamped != value) {
                change.setRange(0, change.getControlText().length());
                change.setText(String.valueOf(clamped));
            }

            return change;
        });

        field.setTextFormatter(formatter);
    }



}
