package ui.components;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Item;
import ui.util.UIHelpers;
import util.IntRange;

import java.util.List;

public class ItemRow extends EntityRow{
    //stats differences made by object
    private int nr;
    private TextField dH;
    private TextField dA;
    private TextField dD;
    private TextField dS;

    private Button remove;

    public ItemRow(IntRange rowRange, IntRange colRange, int nr, Runnable validator) {
        super(rowRange, colRange, validator);

        this.nr = nr;
        dH = UIHelpers.configureSignedNumberField(new IntRange(0, 999), 0, validator);
        dA = UIHelpers.configureSignedNumberField(new IntRange(0, 999), 0, validator);
        dD = UIHelpers.configureSignedNumberField(new IntRange(0, 999), 0, validator);
        dS = UIHelpers.configureSignedNumberField(new IntRange(0, 999), 0, validator);

        super.getFields().addAll(List.of(dH, dA, dD, dS));

        remove = new Button("X");
    }

    public Item toItem() {
        if (getRowField().getText().isEmpty() ||
                getColField().getText().isEmpty() ||
                dH.getText().isEmpty() ||
                dA.getText().isEmpty() ||
                dD.getText().isEmpty() ||
                dS.getText().isEmpty()) {
            return null;
        }

        return new Item(
                nr,
                Integer.parseInt(getRowField().getText()),
                Integer.parseInt(getColField().getText()),
                Integer.parseInt(dH.getText()),
                Integer.parseInt(dA.getText()),
                Integer.parseInt(dD.getText()),
                Integer.parseInt(dS.getText())
        );
    }

    public Button getRemoveButton() {return remove; }

}
