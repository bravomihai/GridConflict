import enginebridge.NativeEngine;
import model.*;
import util.*;
import logic.*;

public class Main {

    public static void main(String[] args) throws Exception {

        GameState gs = new GameState();
        gs.H = 7;
        gs.W = 7;
        gs.currentPlayer = 'A';

        gs.players[0] = new Player(100, 40, 0, 20, 20);
        gs.players[1] = new Player(100, 40, 0, 20, 20);

        gs.items.add(new Item(20, 10, 0, 0));
        gs.items.add(new Item(0, 0, 0, 5));

        gs.encodedMap =
                "A A4 m C2 m C6 m E2 m E6 o0 D4 o1 B4 B G4";

        NativeEngine engine =
                new NativeEngine("engine/build/grid_conflict.exe");

        while (gs.players[0].H > 0 && gs.players[1].H > 0 && gs.consecutivePassRounds < 10) {

            BoardPrinter.printBoard(gs);

            String moveStr = engine.computeMoveAsync(gs).join();
            Move move = Move.fromString(moveStr);

            System.out.println(gs.currentPlayer + " plays: " + moveStr);

            GameLogic.applyMove(gs, move);
        }

        engine.shutdown();
    }
}