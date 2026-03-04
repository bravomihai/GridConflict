package ui.components;

import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import model.Item;
import ui.util.UIHelpers;

public class ObjectRow {

    private HBox root;

    //coordinates
    private Spinner<Integer> row;
    private Spinner<Integer> col;

    //stats differences made by object
    private Spinner<Integer> dH;
    private Spinner<Integer> dA;
    private Spinner<Integer> dD;
    private Spinner<Integer> dS;

    private Button remove;

    public ObjectRow() {

        root = new HBox(10);
        row = new Spinner<>();
        col = new Spinner<>();
        dH = new Spinner<>();
        dA = new Spinner<>();
        dD = new Spinner<>();
        dS = new Spinner<>();

        UIHelpers.configureSpinner(row, 0, 999, 0);
        UIHelpers.configureSpinner(col, 0, 999, 0);
        UIHelpers.configureSpinner(dH, 0, 999, 0);
        UIHelpers.configureSpinner(dA, 0, 999, 0);
        UIHelpers.configureSpinner(dD, 0, 999, 0);
        UIHelpers.configureSpinner(dS, 0, 999, 0);


        root.getChildren().addAll(row, col, dH, dA, dD, dS);
    }

    public HBox getNode() {
        return root;
    }

    public Item toItem() {
        return new Item(
                dH.getValue(),
                dA.getValue(),
                dD.getValue(),
                dS.getValue()
        );
    }
}
