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

        initDifficultySlider();

    }

    public void resize() {

        difficultySlider.setMaxWidth(sceneWidth * 0.1);
        balanceBar.setMaxWidth(sceneWidth * 0.1);
    }

    private void initDifficultySlider(){

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

    private int getDepth() {
        return switch ((int) difficultySlider.getValue()) {
            case 0 -> 3;
            case 1 -> 7;
            case 2 -> 15;
            case 3 -> 30;
            default -> 0;
        };
    }

    public void computeBalance(){
        Path exePath = Paths.get(
                System.getProperty("user.dir"),
                "engine",
                "build",
                "grid_conflict.exe"
        ).toAbsolutePath();

        NativeEngine engine;
        try {
            engine = new NativeEngine(exePath.toString(), getDepth());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        EngineResult res = engine.computeMoveAsync(gameState).join();

        System.out.println(res.winChance);

        balanceBar.setProgress(res.winChance);
        engine.shutdown();
    }

    public void setSceneWidth(double newWidth){
        sceneWidth = newWidth;
    }

    public void setGameState(GameState gs){

        this.gameState = gs;

        gameState.validProperty().addListener((_, _, valid) -> {

            if (valid) {
                balanceBar.getStyleClass().remove("invalid");
                computeBalance();
            } else {
                if (!balanceBar.getStyleClass().contains("invalid")) {
                    balanceBar.getStyleClass().add("invalid");
                }
                balanceBar.setProgress(1.0);
            }

        });
    }
}
