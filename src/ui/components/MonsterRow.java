package ui.components;

import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import model.Item;
import ui.util.UIHelpers;
import util.IntRange;

import java.util.ArrayList;
import java.util.List;

public class MonsterRow {

    private List<TextField> fields;

    //coordinates
    private TextField row;
    private TextField col;

    private Button remove;

    public MonsterRow(IntRange rowRange, IntRange colRange) {

        fields = new ArrayList<>(10);
        row = UIHelpers.configureTextField(rowRange, 0);
        col = UIHelpers.configureTextField(colRange, 0);

        fields.addAll(List.of(row, col));

        remove = new Button("X");
    }

    public List<TextField> getFields() {
        return fields;
    }

    public Button getRemoveButton() {return remove; }

    public TextField getRowField() {
        return row;
    }

    public TextField getColField() {
        return col;
    }

}
