package ui.util;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class UIAnimations {

    public static void pulsateErrorBorder(TextField field, Runnable validator) {

        Timeline existing = (Timeline) field.getProperties().get("errorPulse");

        if (existing != null && existing.getStatus() == Animation.Status.RUNNING) {
            return;
        }

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        e -> field.setStyle("-fx-border-color: rgba(255,0,0,1);")),
                new KeyFrame(Duration.seconds(0.1),
                        e -> field.setStyle("-fx-border-color: rgba(255,0,0,1);")),
                new KeyFrame(Duration.seconds(0.2),
                        e -> field.setStyle("-fx-border-color: rgba(255,0,0,0.7);")),
                new KeyFrame(Duration.seconds(0.3),
                        e -> field.setStyle("-fx-border-color: rgba(255,0,0,0.4);")),
                new KeyFrame(Duration.seconds(0.4),
                        e -> field.setStyle("-fx-border-color: rgba(255,0,0,0);")),
                new KeyFrame(Duration.seconds(0.5),
                        e -> field.setStyle("-fx-border-color: rgba(255,0,0,1);")),
                new KeyFrame(Duration.seconds(0.6),
                        e -> field.setStyle("-fx-border-color: rgba(255,0,0,1);")),
                new KeyFrame(Duration.seconds(0.7),
                        e -> field.setStyle("-fx-border-color: rgba(255,0,0,0.7);")),
                new KeyFrame(Duration.seconds(0.8),
                        e -> field.setStyle("-fx-border-color: rgba(255,0,0,0.4);")),
                new KeyFrame(Duration.seconds(0.9),
                        e -> field.setStyle("-fx-border-color: rgba(255,0,0,0);"))
        );

        field.getProperties().put("errorPulse", timeline);

        timeline.setOnFinished(e -> {
            field.getProperties().remove("errorPulse");

            if (validator != null) {
                validator.run();
            }
        });

        timeline.play();
    }

}