package ui.components;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Item;
import ui.util.UIHelpers;

import java.util.ArrayList;
import java.util.List;

public class ObjectRow {

    private List<TextField> fields;

    //coordinates
    private TextField row;
    private TextField col;

    //stats differences made by object
    private TextField dH;
    private TextField dA;
    private TextField dD;
    private TextField dS;

    private Button remove;

    public ObjectRow() {
        fields = new ArrayList<TextField>();

        row = UIHelpers.configureTextField(0, 999, 0);
        col = UIHelpers.configureTextField(0, 999, 0);
        dH = UIHelpers.configureTextField(0, 999, 0);
        dA = UIHelpers.configureTextField(0, 999, 0);
        dD = UIHelpers.configureTextField(0, 999, 0);
        dS = UIHelpers.configureTextField(0, 999, 0);

        fields.addAll(List.of(row, col, dH, dA, dD, dS));

        remove = new Button("X");
    }

    public List<TextField> getFields() {
        return fields;
    }

    public Item toItem() {
        return new Item(
                Integer.parseInt(dH.getText()),
                Integer.parseInt(dA.getText()),
                Integer.parseInt(dD.getText()),
                Integer.parseInt(dS.getText())
        );
    }

    public Button getRemoveButton() {return remove; }
}
