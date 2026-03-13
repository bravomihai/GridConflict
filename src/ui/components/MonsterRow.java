package ui.components;

import javafx.scene.control.Button;
import model.Monster;
import util.IntRange;

public class MonsterRow extends EntityRow{
    private Button remove;

    public MonsterRow(IntRange rowRange, IntRange colRange, Runnable validator) {
        super(rowRange, colRange, validator);
        remove = new Button("X");
    }

    public Monster toMonster(){
        if (getRowField().getText().isEmpty() ||
                getColField().getText().isEmpty()){
            return null;
        }

        return new Monster(
                Integer.parseInt(getRowField().getText()),
                Integer.parseInt(getColField().getText())
        );
    }

    public Button getRemoveButton() {return remove; }

}
