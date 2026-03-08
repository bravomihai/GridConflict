import enginebridge.NativeEngine;
import model.*;
import util.*;
import logic.*;

public class Main {

    public static void main(String[] args) throws Exception {
        GameState gs = new GameState();
        gs.H = 52;
        gs.W = 70;
        gs.currentPlayer = 'A';

        gs.players[0] = new Player(52, 40,100, 40, 0, 20, 20);
        gs.players[1] = new Player(7, 4,100, 40, 0, 20, 20);

        gs.items.add(new Item(4, 4,20, 10, 0, 0));
        gs.items.add(new Item(2, 4,0, 0, 0, 5));

        gs.monsters.add(new Monster(3, 2));
        gs.monsters.add(new Monster(3, 6));
        gs.monsters.add(new Monster(5, 2));
        gs.monsters.add(new Monster(5, 6));


        System.out.println(gs.toEncodedMap());

        //"A z40 B G4 o0 D4 o1 B4 m C2 m C6 m E2 m E6"

    }
}