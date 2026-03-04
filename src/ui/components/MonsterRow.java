package ui.components;

import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import model.Item;

public class MonsterRow {

    private HBox root;

    //coordinates
    private Spinner<Integer> row;
    private Spinner<Integer> col;

    private Button remove;

    public MonsterRow() {

        root = new HBox(10);
        row = new Spinner<>();
        col = new Spinner<>();

        root.getChildren().addAll(row, col);
    }

    public HBox getNode() {
        return root;
    }
}
