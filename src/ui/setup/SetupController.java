package ui.setup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import model.GameState;
import model.Point;
import ui.components.EntityRow;
import ui.components.MonsterRow;
import ui.components.PlayerRow;
import ui.core.SceneController;
import ui.game.GameController;
import ui.util.UIHelpers;
import ui.components.ItemRow;
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
    private IntRange rowRange = new IntRange(1, 999);
    private IntRange colRange = new IntRange(1, 999);

    @FXML private GridPane playerGrid;
    List<PlayerRow> playerRows = new ArrayList<>();

    @FXML private GridPane itemGrid;
    @FXML private Button addItemButton;
    List<ItemRow> itemRows = new ArrayList<>();

    @FXML private GridPane monsterGrid;
    @FXML private Button addMonsterButton;
    List<MonsterRow> monsterRows = new ArrayList<>();

    @FXML Button startGameButton;

    @FXML
    private void initialize() {
        //init map grid
        UIHelpers.configureTextField(mapHeightField, new IntRange(1, 52), 7, this::validateSetup);
        UIHelpers.configureTextField(mapWidthField, new IntRange(1, 99), 7, this::validateSetup);
        updateCoordinateLimits();

        mapHeightField.textProperty().addListener((obs, oldValue, newValue) -> {
            updateCoordinateLimits();
        });

        mapWidthField.textProperty().addListener((obs, oldValue, newValue) -> {
            updateCoordinateLimits();
        });

        //init player grid (no function since its only done once)
        addCoordLabelsToGrid(playerGrid);
        addStatLabelsToGrid(playerGrid);

        PlayerRow  A = new PlayerRow(rowRange, colRange, this::validateSetup);
        PlayerRow B = new PlayerRow(rowRange, colRange, this::validateSetup);
        playerGrid.add(new Label("Player #1"), 0, 1);
        playerGrid.add(new Label("Player #2"), 0, 2);

        addRowToGrid(playerGrid, A, 1, 1);
        addRowToGrid(playerGrid, B, 1, 2);

        addSetupListener(A);
        addSetupListener(B);


        playerRows.addAll(List.of(A, B));
        validateSetup();
    }

    public Node getCell(GridPane grid, int row, int col) {
        for (Node node : grid.getChildren()) {
            if (GridPane.getRowIndex(node) == row &&
                    GridPane.getColumnIndex(node) == col) {
                return node;
            }
        }
        return null;
    }

    private void addRowToGrid(GridPane grid, EntityRow e, int startCol, int startRow){
        for (int i = 0; i < e.getFields().size(); i++) {
            grid.add(e.getFields().get(i) , i + startCol, startRow);
        }
    }

    private void addCoordLabelsToGrid(GridPane grid){
        grid.add(new Label("Row"), 1, 0);
        grid.add(new Label("Column"), 2, 0);
    }

    private void addStatLabelsToGrid(GridPane grid){
        grid.add(new Label("Health"), 3, 0);
        grid.add(new Label("Attack"), 4, 0);
        grid.add(new Label("Defense"), 5, 0);
        grid.add(new Label("Stamina"), 6, 0);
    }

    private void validateSetup() {

        Set<Point> occupied = new HashSet<>();
        Set<Point> duplicates = new HashSet<>();

        boolean valid = true;

        List<EntityRow> all = new ArrayList<>();
        all.addAll(playerRows);
        all.addAll(itemRows);
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

        rowRange.setMin(1);
        rowRange.setMax(height);

        colRange.setMin(1);
        colRange.setMax(width);

    }

    private void initObjectGrid(){
        addCoordLabelsToGrid(itemGrid);
        addStatLabelsToGrid(itemGrid);
    }

    private void refreshItemGrid(){
        itemGrid.getChildren().clear();

        if(!itemRows.isEmpty()){
            initObjectGrid();
        }

        if(itemRows.size() < 10){
            addItemButton.setDisable(false);
        }

        if(itemRows.size() >= 10){
            addItemButton.setDisable(true);
        }

        for (int r = 0; r < itemRows.size(); r++) {

            ItemRow obj = itemRows.get(r);

            itemGrid.add(new Label("Item #" + (r+1)), 0, r+1);

            addRowToGrid(itemGrid, obj, 1, r+1);

            itemGrid.add(obj.getRemoveButton(), 7, r + 1);
        }

    }

    public void handleAddItem(ActionEvent actionEvent) {
        ItemRow itemRow = new ItemRow(rowRange, colRange, this::validateSetup);

        addSetupListener(itemRow);

        itemRow.getRemoveButton().setOnAction(e -> {
            itemRows.remove(itemRow);
            refreshItemGrid();
        });

        itemRows.add(itemRow);
        validateSetup();
        refreshItemGrid();
    }

    private void initMonsterGrid(){
        addCoordLabelsToGrid(monsterGrid);
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

            addRowToGrid(monsterGrid, obj, 1, r + 1);

            monsterGrid.add(obj.getRemoveButton(), 3, r + 1);
        }
    }

    public void handleAddMonster(ActionEvent actionEvent) {
        MonsterRow monsterRow  = new MonsterRow(rowRange, colRange, this::validateSetup);

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

    public void handleStartGame(ActionEvent e) throws Exception {

        GameState state = new GameState();

        state.H = Integer.parseInt(mapHeightField.getText());
        state.W = Integer.parseInt(mapWidthField.getText());

        state.players[0] = playerRows.get(0).toPlayer();
        state.players[1] = playerRows.get(1).toPlayer();

        for(ItemRow i : itemRows){
            state.items.add(i.toItem());
        }

        for(MonsterRow m : monsterRows){
            state.monsters.add(m.toMonster());
        }

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/ui/game/Game.fxml")
        );

        Parent root = loader.load();

        GameController controller = loader.getController();
        controller.initGame(state);

        Scene scene = new Scene(root);
        SceneController.getStage().setScene(scene);
    }
}