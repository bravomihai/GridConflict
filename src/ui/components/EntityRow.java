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

    public EntityRow(IntRange rowRange, IntRange colRange, Runnable validator){
        fields = new ArrayList<>();
        row = UIHelpers.configureTextField(rowRange, 1, validator);
        col = UIHelpers.configureTextField(colRange, 1, validator);
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

}
