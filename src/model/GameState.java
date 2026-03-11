package model;

import java.util.*;

import static java.util.Collections.swap;

public class GameState {

    public int consecutivePassRounds = 0;
    public int H;
    public int W;
    public int playerIndex = 0;
    public int opponentIndex = 1;

    public Player[] players = new Player[2];
    public List<Item> items = new ArrayList<>();
    public List<Monster> monsters = new ArrayList<>();

    public String toEncodedMap(){
        StringBuilder encodedMap = new StringBuilder();

        encodedMap.append("A ")
                .append(indexToRowChar(players[0].row))
                .append(players[0].col);

        encodedMap.append(" B ")
                .append(indexToRowChar(players[1].row))
                .append(players[1].col);

        for(int i = 0 ; i < items.size(); i++){
            encodedMap.append(" o" + i + " " + indexToRowChar(items.get(i).row) + items.get(i).col);
        }

        for(int i = 0 ; i < monsters.size(); i++){
            encodedMap.append(" m " + indexToRowChar(monsters.get(i).row) + monsters.get(i).col);
        }

        return encodedMap.toString();
    }

    public boolean isGameOver(){
        return consecutivePassRounds >= 10 || players[playerIndex].H <= 0 || players[opponentIndex].H <= 0;
    }

    public void applyMove(GameState gs, Move move) throws InvalidMoveException{
        if (gs == null || move == null) return;

        if(move.type == 'p'){
            consecutivePassRounds++;
            players[playerIndex].s = players[playerIndex].S;
            int aux = playerIndex;
            playerIndex = opponentIndex;
            opponentIndex = aux;
            return;
        }

        int moveRow = rowCharToIndex(move.row), moveCol = move.col;

        if(moveRow == -1){
            throw new InvalidMoveException("Invalid move row character");
        }

        if(moveRow < 1 || moveRow > H || moveCol < 1 || moveCol > W){
            throw new InvalidMoveException("Move coordinates out of bounds");
        }

        if(move.type == 'm'){
            consecutivePassRounds = 0;
            int dist = manhattan(
                    rowCharToIndex(move.row),
                    move.col,
                    players[playerIndex].row,
                    players[playerIndex].col
            );

            if(players[playerIndex].s < dist){
                throw new InvalidMoveException("The target is too far away");
            }

            if (monsters.stream()
                    .anyMatch(m -> m.row == moveRow && m.col == move.col)) {
                throw new InvalidMoveException("Target occupied by monster");
            }
            if(players[opponentIndex].row == moveRow && players[opponentIndex].col == moveCol){
                throw new InvalidMoveException("Target occupied by opponent");
            }

            items.stream()
                    .filter(i -> i.row == moveRow && i.col == move.col)
                    .findFirst()
                    .ifPresent(i -> {
                        players[playerIndex].H += i.dH;
                        players[playerIndex].A += i.dA;
                        players[playerIndex].D += i.dD;
                        players[playerIndex].S += i.dS;
                        items.remove(i);
                    });
            players[playerIndex].row = moveRow;
            players[playerIndex].col = move.col;
            players[playerIndex].s -= dist;
        }

        if(move.type == 'a'){
            consecutivePassRounds = 0;
            if(players[playerIndex].s < 10){
                throw new InvalidMoveException("Not enough stamina for the attack");
            }

            if(monsters.stream().noneMatch(m -> m.row == moveRow && m.col == move.col) &&
                    (players[opponentIndex].row != moveRow || players[opponentIndex].col != moveCol)){
                throw new InvalidMoveException("No entity in attacked cell");
            }

            monsters.stream()
                    .filter(m -> m.row == moveRow && m.col == move.col)
                    .findFirst()
                    .ifPresent(m -> {
                        players[playerIndex].H += 10;
                        players[playerIndex].s -= 10;
                        monsters.remove(m);
                    });

            if(players[opponentIndex].row == moveRow && players[opponentIndex].col == moveCol){
                players[opponentIndex].H += Math.min(players[opponentIndex].D - players[playerIndex].A, 0);
                players[playerIndex].s -= 10;
            }
        }

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

}
