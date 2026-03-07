package ui.setup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import model.Point;
import org.w3c.dom.Text;
import ui.components.EntityRow;
import ui.components.MonsterRow;
import ui.components.PlayerRow;
import ui.core.SceneController;
import ui.util.UIHelpers;
import ui.components.ObjectRow;
import util.IntRange;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetupController {
    @FXML private VBox root;

    @FXML private Label titleLabel;

    @FXML private TextField mapHeightField;
    @FXML private TextField mapWidthField;
    private IntRange rowRange = new IntRange(0, 999);
    private IntRange colRange = new IntRange(0, 999);

    @FXML private GridPane playerGrid;
    List<PlayerRow> playerRows = new ArrayList<>();

    @FXML private GridPane objectGrid;
    @FXML private Button addObjectButton;
    List<ObjectRow> objectRows = new ArrayList<>();

    @FXML private GridPane monsterGrid;
    @FXML private Button addMonsterButton;
    List<MonsterRow> monsterRows = new ArrayList<>();

    @FXML Button startGameButton;

    @FXML
    private void initialize() {
        //init map grid
        UIHelpers.configureTextField(mapHeightField, new IntRange(0, 52), 7);
        UIHelpers.configureTextField(mapWidthField, new IntRange(0, 999), 7);
        updateCoordinateLimits();

        mapHeightField.textProperty().addListener((obs, oldValue, newValue) -> {
            updateCoordinateLimits();
        });

        mapWidthField.textProperty().addListener((obs, oldValue, newValue) -> {
            updateCoordinateLimits();
        });

        //init player grid (no function since its only done once)
        playerGrid.add(new Label("Row"), 1, 0);
        playerGrid.add(new Label("Column"), 2, 0);
        playerGrid.add(new Label("Health"), 3, 0);
        playerGrid.add(new Label("Attack"), 4, 0);
        playerGrid.add(new Label("Defense"), 5, 0);
        playerGrid.add(new Label("Stamina"), 6, 0);

        PlayerRow  A = new PlayerRow(rowRange, colRange);
        playerGrid.add(new Label("Player #1"), 0, 1);
        for (int i = 0; i < A.getFields().size(); i++) {
            playerGrid.add(A.getFields().get(i) , i + 1, 1);
        }

        PlayerRow  B = new PlayerRow(rowRange, colRange);
        playerGrid.add(new Label("Player #2"), 0, 2);
        for (int i = 0; i < B.getFields().size(); i++) {
            playerGrid.add(B.getFields().get(i), i + 1, 2);
        }

        addSetupListener(A);
        addSetupListener(B);

        playerRows.addAll(List.of(A, B));
        validateSetup();
    }

    private void validateSetup() {

        Set<Point> occupied = new HashSet<>();
        Set<Point> duplicates = new HashSet<>();

        boolean valid = true;

        List<EntityRow> all = new ArrayList<>();
        all.addAll(playerRows);
        all.addAll(objectRows);
        all.addAll(monsterRows);

        for (EntityRow e : all) {

            String rowText = e.getRowField().getText();
            String colText = e.getColField().getText();

            if (rowText.isEmpty() || colText.isEmpty()) continue;

            int r = Integer.parseInt(rowText);
            int c = Integer.parseInt(colText);

            Point p = new Point(r, c);

            if (!occupied.add(p)) {
                duplicates.add(p);
            }
        }


        for (EntityRow e : all) {

            String rowText = e.getRowField().getText();
            String colText = e.getColField().getText();

            if (rowText.isEmpty() || colText.isEmpty()) {
                e.getRowField().setStyle("");
                e.getColField().setStyle("");
                markInvalid(e, false);
                valid = false;
                continue;
            }

            int r = Integer.parseInt(rowText);
            int c = Integer.parseInt(colText);

            Point p = new Point(r, c);

            if(duplicates.contains(p) || e.getFields().stream().anyMatch(f -> f.getText().isEmpty()))
                valid = false;

            markInvalid(e, duplicates.contains(p));
            markValid(e, duplicates.contains(p));
        }

        startGameButton.setDisable(!valid);
    }

    public void addSetupListener(EntityRow e){
        e.getFields().stream().forEach(f -> f.textProperty().addListener((obs,o,n)->validateSetup()));
    }

    private void markInvalid(EntityRow e, boolean duplicate) {
        e.getFields().stream() .filter(f -> f.getText().isEmpty())
                .forEach(f -> f.setStyle("-fx-border-color: red;"));
        if(duplicate){
            e.getRowField().setStyle("-fx-border-color:red;");
            e.getColField().setStyle("-fx-border-color:red;");
        }
    }

    private void markValid(EntityRow e, boolean duplicate) {
        e.getFields().stream().skip(2).filter(f->!f.getText().isEmpty() ).forEach(f -> f.setStyle(""));
        if(!duplicate){
            e.getRowField().setStyle("");
            e.getColField().setStyle("");
        }
    }

    private void updateCoordinateLimits() {

        int height = Integer.parseInt(mapHeightField.getText());
        int width = Integer.parseInt(mapWidthField.getText());

        rowRange.setMin(0);
        rowRange.setMax(height - 1);

        colRange.setMin(0);
        colRange.setMax(width - 1);

    }

    private void initObjectGrid(){
        objectGrid.add(new Label("Row"), 1, 0);
        objectGrid.add(new Label("Column"), 2, 0);
        objectGrid.add(new Label("Health"), 3, 0);
        objectGrid.add(new Label("Attack"), 4, 0);
        objectGrid.add(new Label("Defense"), 5, 0);
        objectGrid.add(new Label("Stamina"), 6, 0);
    }

    private void refreshObjectGrid(){
        objectGrid.getChildren().clear();

        if(!objectRows.isEmpty()){
            initObjectGrid();
        }

        if(objectRows.size() < 10){
            addObjectButton.setDisable(false);
        }

        if(objectRows.size() >= 10){
            addObjectButton.setDisable(true);
        }

        for (int r = 0; r < objectRows.size(); r++) {

            ObjectRow obj = objectRows.get(r);

            objectGrid.add(new Label("Object #" + (r+1)), 0, r+1);

            for (int c = 0; c < obj.getFields().size(); c++) {
                objectGrid.add(obj.getFields().get(c), c+1, r + 1);
            }

            objectGrid.add(obj.getRemoveButton(), 7, r + 1);
        }

    }

    public void handleAddObject(ActionEvent actionEvent) {
        ObjectRow objectRow = new ObjectRow(rowRange, colRange);

        addSetupListener(objectRow);

        objectRow.getRemoveButton().setOnAction(e -> {
            objectRows.remove(objectRow);
            refreshObjectGrid();
        });

        objectRows.add(objectRow);
        validateSetup();
        refreshObjectGrid();
    }

    private void initMonsterGrid(){
        monsterGrid.add(new Label("Row"), 1, 0);
        monsterGrid.add(new Label("Column"), 2, 0);
    }

    private void refreshMonsterGrid() {
        monsterGrid.getChildren().clear();

        if(!monsterRows.isEmpty()){
            initMonsterGrid();
        }

        if(monsterRows.size() < 15){
            addMonsterButton.setDisable(false);
        }

        if(monsterRows.size() >= 15){
            addMonsterButton.setDisable(true);
        }

        for (int r = 0; r < monsterRows.size(); r++) {

            MonsterRow obj = monsterRows.get(r);

            monsterGrid.add(new Label("Monster #"+ (r+1)), 0, r+1);

            for (int c = 0; c < obj.getFields().size(); c++) {
                monsterGrid.add(obj.getFields().get(c), c+1, r + 1);
            }

            monsterGrid.add(obj.getRemoveButton(), 3, r + 1);
        }
    }

    public void handleAddMonster(ActionEvent actionEvent) {
        MonsterRow monsterRow  = new MonsterRow(rowRange, colRange);

        addSetupListener(monsterRow);

        monsterRow.getRemoveButton().setOnAction( e-> {
            monsterRows.remove(monsterRow);
            refreshMonsterGrid();
        });

        monsterRows.add(monsterRow);
        validateSetup();
        refreshMonsterGrid();
    }


    public void handleBackToMenu(ActionEvent actionEvent) {
        SceneController.switchTo("/ui/menu/Menu.fxml");
    }

    public void handleStartGame(ActionEvent actionEvent) { SceneController.switchTo("/ui/game/Game.fxml");}
}