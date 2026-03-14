package content;

import model.*;
import java.util.List;
import java.util.ArrayList;

public class PresetGameStates {

    private final List<GameState> presets = new ArrayList<>();

    public PresetGameStates() {

        // ----- preset 1 (52x99) -----
        {
            GameState gs = new GameState();

            gs.setHeight(52);
            gs.setWidth(99);

            // players: row, col, H, A, D, S
            Player pA = new Player(1, 50, 100, 40, 0, 20);   // A50
            Player pB = new Player(52, 50, 100, 40, 0, 20);  // z50
            gs.setPlayers(List.of(pA, pB));

            // items: (row, col, dH, dA, dD, dS) o0..o9
            Item it0 = new Item(0, 4, 50, 20, 5, 0, 0);    // o0 D50
            Item it1 = new Item(1, 7, 10, 0, 15, 0, 0);    // o1 G10
            Item it2 = new Item(2, 7, 90, 0, 0, 15, 0);    // o2 G90
            Item it3 = new Item(3, 12, 50, 0, 0, 0, 10);   // o3 L50
            Item it4 = new Item(4, 17, 15, -10, 10, 0, 0); // o4 Q15
            Item it5 = new Item(5, 17, 85, 30, 0, 0, 0);   // o5 Q85
            Item it6 = new Item(6, 22, 50, 0, -10, 20, 0); // o6 V50
            Item it7 = new Item(7, 24, 5, 0, 0, -5, 15);   // o7 X5
            Item it8 = new Item(8, 24, 95, 25, 5, 5, 0);   // o8 X95
            Item it9 = new Item(9, 26, 50, -20, 0, 0, 20); // o9 Z50

            gs.setItems(List.of(it0, it1, it2, it3, it4, it5, it6, it7, it8, it9));

            // monsters: (row, col)
            Monster m1 = new Monster(3, 30);   // C30
            Monster m2 = new Monster(3, 70);   // C70
            Monster m3 = new Monster(8, 20);   // H20
            Monster m4 = new Monster(8, 80);   // H80
            Monster m5 = new Monster(13, 40);  // M40
            Monster m6 = new Monster(13, 60);  // M60
            Monster m7 = new Monster(20, 25);  // T25
            Monster m8 = new Monster(20, 75);  // T75
            Monster m9 = new Monster(25, 45);  // Y45
            Monster m10= new Monster(25, 55);  // Y55

            gs.setMonsters(List.of(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10));

            presets.add(gs);
        }

        // ----- preset 2 (11x11) -----
        {
            GameState gs = new GameState();

            gs.setHeight(11);
            gs.setWidth(11);

            // players: positions from map A6 and K6
            Player pA = new Player(1, 6, 100, 40, 0, 20);  // A6
            Player pB = new Player(11, 6, 100, 40, 0, 20); // K6
            gs.setPlayers(List.of(pA, pB));

            // items (5)
            Item it0 = new Item(0, 4, 6, 15, 0, 0, 0);  // o0 D6
            Item it1 = new Item(1, 6, 6, 0, 10, 0, 0);  // o1 F6
            Item it2 = new Item(2, 8, 6, 0, 0, 10, 0);  // o2 H6
            Item it3 = new Item(3, 5, 2, 0, 0, 0, 5);   // o3 E2
            Item it4 = new Item(4, 7,10, -10,5,0,0);    // o4 G10

            gs.setItems(List.of(it0, it1, it2, it3, it4));

            // monsters
            Monster m1 = new Monster(3, 4);  // C4
            Monster m2 = new Monster(3, 8);  // C8
            Monster m3 = new Monster(6, 3);  // F3
            Monster m4 = new Monster(6, 9);  // F9
            Monster m5 = new Monster(9, 4);  // I4
            Monster m6 = new Monster(9, 8);  // I8

            gs.setMonsters(List.of(m1, m2, m3, m4, m5, m6));

            presets.add(gs);
        }

        // ----- preset 3 (7x7) -----
        {
            GameState gs = new GameState();

            gs.setHeight(7);
            gs.setWidth(7);

            // players A at A4, B at G4
            Player pA = new Player(1, 4, 100, 40, 0, 20);
            Player pB = new Player(7, 4, 100, 40, 0, 20);
            gs.setPlayers(List.of(pA, pB));

            // items (2)
            Item it0 = new Item(0, 4, 4, 20, 10, 0, 0); // o0 D4
            Item it1 = new Item(1, 2, 4, 0, 0, 0, 5);   // o1 B4

            gs.setItems(List.of(it0, it1));

            // monsters
            Monster m1 = new Monster(3, 2); // C2
            Monster m2 = new Monster(3, 6); // C6
            Monster m3 = new Monster(5, 2); // E2
            Monster m4 = new Monster(5, 6); // E6

            gs.setMonsters(List.of(m1, m2, m3, m4));

            presets.add(gs);
        }

        // ----- preset 4 (3x3) -----
        {
            GameState gs = new GameState();

            gs.setHeight(3);
            gs.setWidth(3);

            // players A at A1, B at C3
            Player pA = new Player(1, 1, 100, 40, 0, 20);
            Player pB = new Player(3, 3, 100, 40, 0, 20);
            gs.setPlayers(List.of(pA, pB));

            // no items
            gs.setItems(List.of());

            // monsters on A2, A3, B1, B2, B3, C1, C2
            Monster m1 = new Monster(1, 2); // A2
            Monster m2 = new Monster(1, 3); // A3
            Monster m3 = new Monster(2, 1); // B1
            Monster m4 =  new Monster(2, 2); // B2
            Monster m5 = new Monster(2, 3); // B3
            Monster m6 = new Monster(3, 1); // C1
            Monster m7 = new Monster(3, 2); // C2

            gs.setMonsters(List.of(m1, m2, m3, m4, m5, m6, m7));

            presets.add(gs);
        }
    }

    public GameState get(int index) {
        return new GameState(presets.get(index));
    }
}