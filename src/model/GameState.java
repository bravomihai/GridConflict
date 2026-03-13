package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.*;

public class GameState {

    private final BooleanProperty valid = new SimpleBooleanProperty(false);

    private int consecutivePassRounds = 0;
    private int H = 2;
    private int W = 2;
    private int playerIndex = 0;
    private int opponentIndex = 1;

    private List<Player> players = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
    private List<Monster> monsters = new ArrayList<>();

    private static final int Sho = 32768;

    public GameState(){
        this.players = java.util.List.of(
                        new Player(1, 1, 100, 40, 0, 20),
                        new Player(2, 2, 100, 40, 0, 20)
                );
    }

    public GameState(GameState other) {

        this.consecutivePassRounds = other.consecutivePassRounds;
        this.H = other.H;
        this.W = other.W;
        this.playerIndex = other.playerIndex;
        this.opponentIndex = other.opponentIndex;

        this.players = new ArrayList<>();
        for (Player p : other.players) {
            this.players.add(new Player(
                    p.row,
                    p.col,
                    p.H,
                    p.A,
                    p.D,
                    p.S
            ));
        }

        this.items = new ArrayList<>();
        for (Item i : other.items) {
            this.items.add(new Item(
                    i.nr,
                    i.row,
                    i.col,
                    i.dH,
                    i.dA,
                    i.dD,
                    i.dS
            ));
        }

        this.monsters = new ArrayList<>();
        for (Monster m : other.monsters) {
            this.monsters.add(new Monster(
                    m.row,
                    m.col
            ));
        }

        computeValidity();
    }

    // getters / setters

    public BooleanProperty validProperty() {
        return valid;
    }

    public boolean isValid() {
        return valid.get();
    }

    private void computeValidity() {
        valid.set(validate());
    }

    public int getConsecutivePassRounds() {
        return consecutivePassRounds;
    }

    public int getHeight() {
        return H;
    }

    public void setHeight(int h) {
        this.H = h;
        computeValidity();
    }

    public int getWidth() {
        return W;
    }

    public void setWidth(int w) {
        this.W = w;
        computeValidity();
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public int getOpponentIndex() {
        return opponentIndex;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = new ArrayList<>(players);
        computeValidity();
    }

    public Player getPlayer(int index) {
        return players.get(index);
    }

    public void setPlayer(int index, Player player) {
        this.players.set(index, player);
        computeValidity();
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = new ArrayList<>(items);
        computeValidity();
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = new ArrayList<>(monsters);
        computeValidity();
    }

    public String toEncodedMap(){
        StringBuilder encodedMap = new StringBuilder();

        encodedMap.append("A ")
                .append(indexToRowChar(players.get(0).row))
                .append(players.get(0).col);

        encodedMap.append(" B ")
                .append(indexToRowChar(players.get(1).row))
                .append(players.get(1).col);

        for(int i = 0 ; i < items.size(); i++){
            encodedMap.append(" o").append(i).append(" ")
                    .append(indexToRowChar(items.get(i).row))
                    .append(items.get(i).col);
        }

        for (Monster monster : monsters) {
            encodedMap.append(" m ")
                    .append(indexToRowChar(monster.row))
                    .append(monster.col);
        }

        return encodedMap.toString();
    }

    public boolean isGameOver(){
        return consecutivePassRounds >= 10 ||
                players.get(playerIndex).H <= 0 ||
                players.get(opponentIndex).H <= 0;
    }

    public void applyMove(Move move) throws InvalidMoveException {
        if (move == null) return;

        //critical error
        if(move.type == 'c'){
            throw new InvalidMoveException("Critical error, invalid gamestate fed to engine!");
        }

        // pass
        if (move.type == 'p') {
            consecutivePassRounds++;
            switchTurn();
            return;
        }

        int moveRow = rowCharToIndex(move.row);
        int moveCol = move.col;

        if (moveRow < 0) {
            throw new InvalidMoveException("Invalid move row character");
        }

        if (moveRow < 1 || moveRow > H || moveCol < 1 || moveCol > W) {
            throw new InvalidMoveException("Move coordinates out of bounds");
        }

        // move (walk)
        if (move.type == 'm') {
            consecutivePassRounds = 0;

            int dist = manhattan(
                    rowCharToIndex(move.row),
                    move.col,
                    players.get(playerIndex).row,
                    players.get(playerIndex).col
            );

            if (players.get(playerIndex).s < dist) {
                throw new InvalidMoveException("The target is too far away");
            }

            if (monsters.stream().anyMatch(m -> m.row == moveRow && m.col == moveCol)) {
                throw new InvalidMoveException("Target occupied by monster");
            }
            if (players.get(opponentIndex).row == moveRow && players.get(opponentIndex).col == moveCol) {
                throw new InvalidMoveException("Target occupied by opponent");
            }

            // pick up item if present
            Optional<Item> optItem = items.stream()
                    .filter(i -> i.row == moveRow && i.col == moveCol)
                    .findFirst();
            if (optItem.isPresent()) {
                Item i = optItem.get();
                players.get(playerIndex).H += i.dH;
                players.get(playerIndex).A += i.dA;
                players.get(playerIndex).D += i.dD;
                players.get(playerIndex).S += i.dS;
                items.remove(i);
            }

            players.get(playerIndex).row = moveRow;
            players.get(playerIndex).col = move.col;
            players.get(playerIndex).s -= dist;
        }

        // attack
        if (move.type == 'a') {
            consecutivePassRounds = 0;

            if (players.get(playerIndex).s < 10) {
                throw new InvalidMoveException("Not enough stamina for the attack");
            }

            boolean monsterAtTarget = monsters.stream().anyMatch(m -> m.row == moveRow && m.col == moveCol);
            boolean opponentAtTarget = players.get(opponentIndex).row == moveRow && players.get(opponentIndex).col == moveCol;

            if (!monsterAtTarget && !opponentAtTarget) {
                throw new InvalidMoveException("No entity in attacked cell");
            }

            Optional<Monster> optMonster = monsters.stream()
                    .filter(m -> m.row == moveRow && m.col == moveCol)
                    .findFirst();
            if (optMonster.isPresent()) {
                Monster m = optMonster.get();
                players.get(playerIndex).H += 10;
                players.get(playerIndex).s -= 10;
                monsters.remove(m);
            }

            if (opponentAtTarget) {
                players.get(opponentIndex).H += Math.min(players.get(opponentIndex).D - players.get(playerIndex).A, 0);
                players.get(playerIndex).s -= 10;
            }
        }
    }

    public void switchTurn(){
        players.get(playerIndex).s = players.get(playerIndex).S;
        int aux = playerIndex;
        playerIndex = opponentIndex;
        opponentIndex = aux;
    }

    private static int manhattan(int rowA, int colA, int rowB, int colB) {
        return Math.abs(rowA- rowB) + Math.abs(colA - colB);
    }

    private static int rowCharToIndex(char c) {
        if (c >= 'A' && c <= 'Z') return c - 'A' + 1;
        if (c >= 'a' && c <= 'z') return (c - 'a') + 27;
        return -1;
    }

    public char indexToRowChar(int rowIndex){
        if(rowIndex <= 26){
            return (char) ('A' + rowIndex - 1);
        }
        else{
            return (char) ('a' + rowIndex - 27);
        }
    }

    private boolean outOfBounds(Point p) {
        return p.row < 1 || p.row > H || p.col < 1 || p.col > W;
    }

    public boolean validate() {

        Set<Point> occupied = new HashSet<>();

        if (H < 2 || W < 2 || H > 52 || W > 99) return false;

        if (players.size() < 2 || players.get(0) == null || players.get(1) == null) return false;

        for (Player p : players) {
            Point pt = new Point(p.row, p.col);
            if (outOfBounds(pt) || !occupied.add(pt) || p.row > Short.MAX_VALUE || p.col > Short.MAX_VALUE ||
                    p.H > Short.MAX_VALUE || p.A > Short.MAX_VALUE || p.D > Short.MAX_VALUE || p.S > Short.MAX_VALUE) return false;
        }

        for (Item i : items) {
            Point pt = new Point(i.row, i.col);
            if (outOfBounds(pt) || !occupied.add(pt) || i.row > Short.MAX_VALUE || i.col > Short.MAX_VALUE ||
                    i.dH > Short.MAX_VALUE || i.dA > Short.MAX_VALUE || i.dD > Short.MAX_VALUE || i.dS > Short.MAX_VALUE) return false;
        }

        for (Monster m : monsters) {
            Point pt = new Point(m.row, m.col);
            if (outOfBounds(pt) || !occupied.add(pt) || m.row > Short.MAX_VALUE || m.col > Short.MAX_VALUE) return false;
        }

        return true;
    }

}
