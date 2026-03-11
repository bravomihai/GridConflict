package ui.setup;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.util.StringConverter;

public class GameSetupController {

    @FXML public Slider difficultySlider;

    @FXML
    private void initialize(){
        difficultySlider.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                difficultySlider.maxWidthProperty().bind(
                        newScene.widthProperty().multiply(0.1)
                );
            }
        });

        difficultySlider.setLabelFormatter(new StringConverter<Double>() {
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
}
