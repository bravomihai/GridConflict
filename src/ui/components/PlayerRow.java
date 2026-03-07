package ui.components;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Item;
import ui.util.UIHelpers;
import util.IntRange;

import java.util.ArrayList;
import java.util.List;

public class PlayerRow {

    private List<TextField> fields;

    //coordinates
    private TextField row;
    private TextField col;

    //stats differences made by object
    private TextField H;
    private TextField A;
    private TextField D;
    private TextField S;

    public PlayerRow(IntRange rowRange, IntRange colRange) {
        fields = new ArrayList<TextField>();

        row = UIHelpers.configureTextField(rowRange, 0);
        col = UIHelpers.configureTextField(colRange, 0);
        H = UIHelpers.configureTextField(new IntRange(0, 999), 100);
        A = UIHelpers.configureTextField(new IntRange(0, 999), 40);
        D = UIHelpers.configureTextField(new IntRange(0, 999), 0);
        S = UIHelpers.configureTextField(new IntRange(0, 999), 20);

        fields.addAll(List.of(row, col, H, A, D, S));

    }

    public List<TextField> getFields() {
        return fields;
    }

    public Item toItem() {
        return new Item(
                Integer.parseInt(H.getText()),
                Integer.parseInt(A.getText()),
                Integer.parseInt(D.getText()),
                Integer.parseInt(S.getText())
        );
    }

    public TextField getRowField() {
        return row;
    }

    public TextField getColField() {
        return col;
    }

}
