import content.PresetGameStates;
import enginebridge.NativeEngine;
import model.*;
import util.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static util.BoardPrinter.printBoard;

public class Main {

    public static void main(String[] args) {

        PresetGameStates presetGameStates = new PresetGameStates();

        GameState gs = presetGameStates.get(3);

        Path exePath = Paths.get(
                System.getProperty("user.dir"),
                "engine",
                "build",
                "grid_conflict.exe"
        ).toAbsolutePath();

        NativeEngine engine;
        try {
            engine = new NativeEngine(exePath.toString(), 30);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        printBoard(gs);

        while (!gs.isGameOver()) {

            EngineResult res = engine.computeMoveAsync(gs).join();

            System.out.println(
                    res.move.type + " " +
                            res.move.row + " " +
                            res.move.col + " " +
                            res.score + " " +
                            res.winChance
            );

            try {
                gs.applyMove(res.move);
            } catch (InvalidMoveException e) {
                throw new RuntimeException(e);
            }

            printBoard(gs);
        }

        engine.shutdown();
    }
}