import enginebridge.NativeEngine;
import model.*;
import util.*;

import java.util.concurrent.CompletableFuture;

import static util.BoardPrinter.printBoard;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws Exception {
        GameState gs = new GameState();
        gs.H = 7;
        gs.W = 7;

        gs.players[0] = new Player(1, 4,100, 40, 0, 20);
        gs.players[1] = new Player(7, 4,100, 40, 0, 20);

        gs.items.add(new Item(0,4, 4,20, 10, 0, 0));
        gs.items.add(new Item(1,2, 4,0, 0, 0, 5));

        gs.monsters.add(new Monster(3, 2));
        gs.monsters.add(new Monster(3, 6));
        gs.monsters.add(new Monster(5, 2));
        gs.monsters.add(new Monster(5, 6));



        Path exePath = Paths.get(
                System.getProperty("user.dir"),
                "engine",
                "build",
                "grid_conflict.exe"
        ).toAbsolutePath();

        NativeEngine engine = new NativeEngine(exePath.toString(), 30);

        printBoard(gs);

        while(!gs.isGameOver()){

            EngineResult res = engine.computeMoveAsync(gs).join();

            System.out.println(res.move.type + " " +
                    res.move.row + " " +
                    res.move.col + " " +
                    res.score + " " +
                    res.winChance
            );

            gs.applyMove(gs, res.move);

            printBoard(gs);
        }



        engine.shutdown();

        //"A z40 B G4 o0 D4 o1 B4 m C2 m C6 m E2 m E6"

    }
}