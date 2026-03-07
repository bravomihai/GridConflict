package ui.components;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Item;
import ui.util.UIHelpers;
import util.IntRange;

import java.util.ArrayList;
import java.util.List;

public class ObjectRow extends EntityRow{
    //stats differences made by object
    private TextField dH;
    private TextField dA;
    private TextField dD;
    private TextField dS;

    private Button remove;

    public ObjectRow(IntRange rowRange, IntRange colRange) {
        super(rowRange, colRange);

        dH = UIHelpers.configureTextField(new IntRange(0, 999), 0);
        dA = UIHelpers.configureTextField(new IntRange(0, 999), 0);
        dD = UIHelpers.configureTextField(new IntRange(0, 999), 0);
        dS = UIHelpers.configureTextField(new IntRange(0, 999), 0);

        super.getFields().addAll(List.of(dH, dA, dD, dS));

        remove = new Button("X");
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
