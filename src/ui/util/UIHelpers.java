package ui.util;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import util.IntRange;

import static ui.util.UIAnimations.pulsateErrorBorder;

public class UIHelpers {

    public static TextField configureUnsignedNumberField(IntRange range, int defaultValue, Runnable validator) {

        TextField field = new TextField();
        field.setText(String.valueOf(defaultValue));

        field.setTextFormatter(createNumberFormatter(field, range, false, validator));

        return field;
    }

    public static void configureUnsignedNumberField(TextField field, IntRange range, int defaultValue, Runnable validator) {

        field.setText(String.valueOf(Math.max(defaultValue, 2)));

        field.setTextFormatter(createNumberFormatter(field, range, false, validator));

    }

    public static TextField configureSignedNumberField(IntRange range, int defaultValue, Runnable validator) {

        TextField field = new TextField();
        field.setText(String.valueOf(defaultValue));

        field.setTextFormatter(createNumberFormatter(field, range, true, validator));

        return field;
    }

    private static TextFormatter<?> createNumberFormatter(
            TextField field,
            IntRange range,
            boolean signed,
            Runnable validator) {

        String regex = signed ? "-?\\d+" : "\\d+";

        return new TextFormatter<>(change -> {

            String newText = change.getControlNewText();

            if (newText.isEmpty() || (signed && newText.equals("-"))) {
                return change;
            }

            if (!newText.matches(regex)) {
                pulsateErrorBorder(field, validator);
                return null;
            }

            int value;
            try {
                value = Integer.parseInt(newText);
            } catch (NumberFormatException e) {
                pulsateErrorBorder(field, validator);
                return null;
            }

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
    }

}