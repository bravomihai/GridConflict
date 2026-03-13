package ui.components;

import javafx.scene.control.TextField;
import ui.util.UIHelpers;
import util.IntRange;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityRow {
    private List<TextField> fields;

    //coordinates
    private TextField row;
    private TextField col;

    private static final int EMPTY = Short.MAX_VALUE + 1;

    public EntityRow(IntRange rowRange, IntRange colRange, Runnable validator){
        fields = new ArrayList<>();
        row = UIHelpers.configureUnsignedNumberField(rowRange, 1, validator);
        col = UIHelpers.configureUnsignedNumberField(colRange, 1, validator);
        fields.addAll(List.of(row, col));
    }

    public TextField getRowField() {
        return row;
    }

    public TextField getColField() {
        return col;
    }

    public List<TextField> getFields() {
        return fields;
    }

    protected int parseOrSentinel(String s) {
        return s.isEmpty() ? EMPTY : Integer.parseInt(s);
    }

}
