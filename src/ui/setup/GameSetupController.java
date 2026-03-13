package ui.setup;

import enginebridge.NativeEngine;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.util.StringConverter;
import model.EngineResult;
import model.GameState;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GameSetupController {

    @FXML public ProgressBar balanceBar;
    @FXML public Slider difficultySlider;

    private GameState gameState;

    private double sceneWidth;

    @FXML
    private void initialize(){



        initBalanceBar();

        initDifficultySlider();

    }

    public void resize(){

    }

    private void recomputeBalance(){
        Path exePath = Paths.get(
                System.getProperty("user.dir"),
                "engine",
                "build",
                "grid_conflict.exe"
        ).toAbsolutePath();

        int depth;

        switch ((int)difficultySlider.getValue()) {
            case 0 -> depth = 1;
            case 1 -> depth = 6;
            case 2 -> depth = 15;
            case 3 -> depth = 30;
            default -> depth = 1;
        };

        NativeEngine engine;
        try {
            engine = new NativeEngine(exePath.toString(), depth);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        EngineResult res = engine.computeMoveAsync(gameState).join();

        System.out.println(depth + " " + res.winChance);


        balanceBar.setProgress(res.winChance);
        engine.shutdown();
    }


    private void initBalanceBar(){
        balanceBar.sceneProperty().addListener((_, _, newScene) -> {
            if (newScene != null) {
                difficultySlider.maxWidthProperty().bind(
                        newScene.widthProperty().multiply(0.1)
                );
            }
        });
    }

    private void initDifficultySlider(){
        difficultySlider.sceneProperty().addListener((_, _, newScene) -> {
            if (newScene != null) {
                difficultySlider.maxWidthProperty().bind(
                        newScene.widthProperty().multiply(0.1)
                );
            }
        });

        difficultySlider.valueProperty().addListener((_, _, newVal) -> {
            recomputeBalance();
        });

        difficultySlider.setLabelFormatter(new StringConverter<>() {
            @Override
            public String toString(Double value) {
                return switch (value.intValue()) {
                    case 0 -> "Easy";
                    case 1 -> "Normal";
                    case 2 -> "Hard";
                    case 3 -> "Very Hard";
                    default -> "";
                };
            }

            @Override
            public Double fromString(String string) {
                return 0.0;
            }
        });
    }

    public void setSceneWidth(double newWidth){
        sceneWidth = newWidth;
    }


    public void setGameState(GameState gs){
        this.gameState = gs;
    }
}
