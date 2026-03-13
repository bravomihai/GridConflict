package ui.setup;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;

import model.*;
import ui.components.EntityRow;
import ui.components.MonsterRow;
import ui.components.PlayerRow;
import ui.util.UIHelpers;
import ui.components.ItemRow;
import util.IntRange;

import java.util.*;


public class MapSetupController {
    @FXML public TextField mapHeightField;
    @FXML public TextField mapWidthField;
    private final IntRange rowRange = new IntRange(1, 999);
    private final IntRange colRange = new IntRange(1, 999);

    @FXML public GridPane playerGrid;
    public final List<PlayerRow> playerRows = new ArrayList<>();

    @FXML public GridPane itemGrid;
    @FXML public Button addItemButton;
    public final List<ItemRow> itemRows = new ArrayList<>();

    @FXML public GridPane monsterGrid;
    @FXML public Button addMonsterButton;
    public final List<MonsterRow> monsterRows = new ArrayList<>();

    private GameState gameState;

    private double sceneWidth;

    private Runnable onSetupChanged;

    private static final int EMPTY = Short.MAX_VALUE + 1;

    @FXML
    private void initialize() {

    }

    public void setOnSetupChanged(Runnable r) {
        this.onSetupChanged = r;
    }

    private void triggerSetupChanged() {
        if (onSetupChanged != null && gameState.isValid()) {
            onSetupChanged.run();
        }
    }

    public void resize() {

        double fieldWidth = sceneWidth * 0.03;

        mapHeightField.setPrefWidth(fieldWidth);
        mapWidthField.setPrefWidth(fieldWidth);

        for (PlayerRow r : playerRows) {
            r.getFields().forEach(f -> f.setPrefWidth(fieldWidth));
        }

        for (ItemRow r : itemRows) {
            r.getFields().forEach(f -> f.setPrefWidth(fieldWidth));
        }

        for (MonsterRow r : monsterRows) {
            r.getFields().forEach(f -> f.setPrefWidth(fieldWidth));
        }
    }

    public void setSceneWidth(double newWidth){
        sceneWidth = newWidth;
    }

    private void addRowToGrid(GridPane grid, EntityRow e, int row){
        for (int i = 0; i < e.getFields().size(); i++) {
            grid.add(e.getFields().get(i) , i + 1, row);
        }
    }

    private void addCoordinateLabelsToGrid(GridPane grid){
        grid.add(new Label("Row"), 1, 0);
        grid.add(new Label("Column"), 2, 0);
    }

    private void addStatisticLabelsToGrid(GridPane grid){
        grid.add(new Label("Health"), 3, 0);
        grid.add(new Label("Attack"), 4, 0);
        grid.add(new Label("Defense"), 5, 0);
        grid.add(new Label("Stamina"), 6, 0);
    }

    private void updateSetupUI() {

        Set<Point> occupied = new HashSet<>();
        Set<Point> duplicates = new HashSet<>();

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
                continue;
            }

            int r = Integer.parseInt(rowText);
            int c = Integer.parseInt(colText);

            Point p = new Point(r, c);

            if(duplicates.contains(p) || e.getFields().stream().anyMatch(f -> f.getText().isEmpty()))
                markInvalid(e, duplicates.contains(p));

            markValid(e, duplicates.contains(p));
        }
    }

    public void addSetupListener(EntityRow e){
        e.getFields().forEach(f -> f.textProperty().addListener((_, _, _)->{
            updateGameState();
            triggerSetupChanged();
        }));
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

    private void initObjectGrid(){
        addCoordinateLabelsToGrid(itemGrid);
        addStatisticLabelsToGrid(itemGrid);
    }

    private void refreshPlayerGrid() {

        playerGrid.getChildren().clear();

        if (!playerRows.isEmpty()) {
            addCoordinateLabelsToGrid(playerGrid);
            addStatisticLabelsToGrid(playerGrid);
        }

        for (int r = 0; r < playerRows.size(); r++) {

            PlayerRow obj = playerRows.get(r);

            playerGrid.add(new Label("Player #" + (r + 1)), 0, r + 1);

            addRowToGrid(playerGrid, obj, r + 1);
        }
        resize();
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

            addRowToGrid(itemGrid, obj, r+1);

            itemGrid.add(obj.getRemoveButton(), 7, r + 1);
        }

        resize();
    }

    public void handleAddItem() {
        ItemRow itemRow = new ItemRow(rowRange, colRange, itemRows.size(), this::updateSetupUI);

        addSetupListener(itemRow);

        itemRow.getRemoveButton().setOnAction(_ -> {
            itemRows.remove(itemRow);
            updateGameState();
            refreshItemGrid();
            triggerSetupChanged();
        });
        itemRows.add(itemRow);

        refreshItemGrid();
        updateGameState();
        triggerSetupChanged();
    }

    private void initMonsterGrid(){
        addCoordinateLabelsToGrid(monsterGrid);
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

            addRowToGrid(monsterGrid, obj, r + 1);

            monsterGrid.add(obj.getRemoveButton(), 3, r + 1);
        }

        resize();
    }

    public void handleAddMonster() {
        MonsterRow monsterRow  = new MonsterRow(rowRange, colRange, this::updateSetupUI);

        addSetupListener(monsterRow);

        monsterRow.getRemoveButton().setOnAction( _-> {
            monsterRows.remove(monsterRow);
            updateGameState();
            refreshMonsterGrid();
            triggerSetupChanged();

        });
        monsterRows.add(monsterRow);

        refreshMonsterGrid();
        updateGameState();
        triggerSetupChanged();
    }

    public void setGameState(GameState gs) {

        this.gameState = gs;

        playerRows.clear();
        itemRows.clear();
        monsterRows.clear();

        playerGrid.getChildren().clear();
        itemGrid.getChildren().clear();
        monsterGrid.getChildren().clear();

        if (gs == null) return;

        UIHelpers.configureUnsignedNumberField(mapHeightField , new IntRange(2, 52), gs.getHeight(), this::updateSetupUI);
        UIHelpers.configureUnsignedNumberField(mapWidthField, new IntRange(2, 99), gs.getWidth(), this::updateSetupUI);
        mapHeightField.textProperty().addListener((_, _, _) -> updateGameState());
        mapWidthField.textProperty().addListener((_, _, _) -> updateGameState());

        addCoordinateLabelsToGrid(playerGrid);
        addStatisticLabelsToGrid(playerGrid);

        for (int i = 0; i < gs.getPlayers().size(); i++) {

            Player p = gs.getPlayers().get(i);

            PlayerRow row = new PlayerRow(rowRange, colRange, this::updateSetupUI);

            row.getRowField().setText(String.valueOf(p.row));
            row.getColField().setText(String.valueOf(p.col));
            row.getFields().get(2).setText(String.valueOf(p.H));
            row.getFields().get(3).setText(String.valueOf(p.A));
            row.getFields().get(4).setText(String.valueOf(p.D));
            row.getFields().get(5).setText(String.valueOf(p.S));

            addSetupListener(row);

            playerGrid.add(new Label("Player #" + (i + 1)), 0, i + 1);
            addRowToGrid(playerGrid, row, i + 1);

            playerRows.add(row);
        }

        for (Item it : gs.getItems()) {

            ItemRow row = new ItemRow(rowRange, colRange, itemRows.size(), this::updateSetupUI);

            row.getRowField().setText(String.valueOf(it.row));
            row.getColField().setText(String.valueOf(it.col));
            row.getFields().get(2).setText(String.valueOf(it.dH));
            row.getFields().get(3).setText(String.valueOf(it.dA));
            row.getFields().get(4).setText(String.valueOf(it.dD));
            row.getFields().get(5).setText(String.valueOf(it.dS));

            addSetupListener(row);

            row.getRemoveButton().setOnAction( _-> {
                itemRows.remove(row);
                refreshItemGrid();
            });

            itemRows.add(row);
        }

        for (Monster m : gs.getMonsters()) {

            MonsterRow row = new MonsterRow(rowRange, colRange, this::updateSetupUI);

            row.getRowField().setText(String.valueOf(m.row));
            row.getColField().setText(String.valueOf(m.col));

            addSetupListener(row);

            row.getRemoveButton().setOnAction( _-> {
                monsterRows.remove(row);
                refreshMonsterGrid();
            });

            monsterRows.add(row);
        }

        refreshItemGrid();
        refreshMonsterGrid();
        refreshPlayerGrid();
        updateGameState();
    }

    private void updateGameState() {
        if (gameState == null) {
            return;
        }

        if(!mapHeightField.getText().isEmpty()){
            gameState.setHeight(Integer.parseInt(mapHeightField.getText()));
        }
        else{
            gameState.setHeight(EMPTY);
        }

        if(!mapWidthField.getText().isEmpty()){
            gameState.setWidth(Integer.parseInt(mapWidthField.getText()));
        }
        else{
            gameState.setWidth(EMPTY);
        }

        rowRange.setMin(1);
        rowRange.setMax(gameState.getHeight());

        colRange.setMin(1);
        colRange.setMax(gameState.getWidth());

        gameState.setPlayers(
                playerRows.stream().map(PlayerRow::toPlayer).toList()
        );

        gameState.setItems(
                itemRows.stream().map(ItemRow::toItem).toList()
        );

        gameState.setMonsters(
                monsterRows.stream().map(MonsterRow::toMonster).toList()
        );

        updateSetupUI();

    }

}