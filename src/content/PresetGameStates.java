package content;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class PresetGameStates {

    private final List<GameState> presets = new ArrayList<>();

    public PresetGameStates() {

        GameState gs = new GameState();

        gs.setHeight(7);
        gs.setWidth(7);

        gs.setPlayers(
                List.of(
                        new Player(1, 4, 100, 40, 0, 20),
                        new Player(7, 4, 100, 40, 0, 20)
                )
        );

        gs.setItems(
                List.of(
                        new Item(0, 4, 4, 20, 10, 0, 0),
                        new Item(1, 2, 4, 0, 0, 0, 5)
                )
        );

        gs.setMonsters(
                List.of(
                        new Monster(3, 2),
                        new Monster(3, 6),
                        new Monster(5, 2),
                        new Monster(5, 6)
                )
        );

        presets.add(gs);
    }

    public GameState get(int index) {
        return new GameState(presets.get(index));
    }
}