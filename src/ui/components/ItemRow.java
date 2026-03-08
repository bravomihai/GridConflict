package ui.components;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Item;
import ui.util.UIHelpers;
import util.IntRange;

import java.util.List;

public class ItemRow extends EntityRow{
    //stats differences made by object
    private TextField dH;
    private TextField dA;
    private TextField dD;
    private TextField dS;

    private Button remove;

    public ItemRow(IntRange rowRange, IntRange colRange, Runnable validator) {
        super(rowRange, colRange, validator);

        dH = UIHelpers.configureTextField(new IntRange(0, 999), 0, validator);
        dA = UIHelpers.configureTextField(new IntRange(0, 999), 0, validator);
        dD = UIHelpers.configureTextField(new IntRange(0, 999), 0, validator);
        dS = UIHelpers.configureTextField(new IntRange(0, 999), 0, validator);

        super.getFields().addAll(List.of(dH, dA, dD, dS));

        remove = new Button("X");
    }

    public Item toItem() {
        return new Item(
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
