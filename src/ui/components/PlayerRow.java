package ui.components;

import javafx.scene.control.TextField;
import model.Item;
import model.Player;
import ui.util.UIHelpers;
import util.IntRange;

import java.util.List;

public class PlayerRow extends EntityRow{
    //stats differences made by object
    private TextField H;
    private TextField A;
    private TextField D;
    private TextField S;

    public PlayerRow(IntRange rowRange, IntRange colRange, Runnable validator) {
        super(rowRange, colRange, validator);

        H = UIHelpers.configureTextField(new IntRange(0, 999), 100, validator);
        A = UIHelpers.configureTextField(new IntRange(0, 999), 40, validator);
        D = UIHelpers.configureTextField(new IntRange(0, 999), 0, validator);
        S = UIHelpers.configureTextField(new IntRange(0, 999), 20, validator);

        super.getFields().addAll(List.of(H, A, D, S));

    }

    public Player toPlayer() {
        return new Player(
                Integer.parseInt(getRowField().getText()),
                Integer.parseInt(getColField().getText()),
                Integer.parseInt(H.getText()),
                Integer.parseInt(A.getText()),
                Integer.parseInt(D.getText()),
                Integer.parseInt(S.getText())
        );
    }
}
