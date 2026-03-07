package ui.components;

import javafx.scene.control.Button;
import util.IntRange;

public class MonsterRow extends EntityRow{
    private Button remove;

    public MonsterRow(IntRange rowRange, IntRange colRange) {
        super(rowRange, colRange);
        remove = new Button("X");
    }

    public Button getRemoveButton() {return remove; }

}
