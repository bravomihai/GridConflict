package model;

import java.util.*;

public class GameState {

    public int consecutivePassRounds = 0;
    public int H;
    public int W;
    public char currentPlayer;

    public Player[] players = new Player[2];
    public List<Item> items = new ArrayList<>();
    public List<Monster> monsters = new ArrayList<>();

    public String toEngineString() {
        StringBuilder sb = new StringBuilder();

        sb.append(H).append(" ")
                .append(W).append(" ")
                .append(currentPlayer).append("\n");

        Player p0 = players[0];
        sb.append(p0.H).append(" ")
                .append(p0.A).append(" ")
                .append(p0.D).append(" ")
                .append(p0.s).append(" ")
                .append(p0.S).append("\n");

        Player p1 = players[1];
        sb.append(p1.H).append(" ")
                .append(p1.A).append(" ")
                .append(p1.D).append(" ")
                .append(p1.s).append(" ")
                .append(p1.S).append("\n");

        sb.append(items.size()).append("\n");

        for (Item it : items) {
            sb.append(it.dH).append(" ")
                    .append(it.dA).append(" ")
                    .append(it.dD).append(" ")
                    .append(it.dS).append("\n");
        }

        sb.append(toEncodedMap());

        return sb.toString();
    }

    public char indexToRow(int rowIndex){
        if(rowIndex <= 26){
            return (char) ('A' + rowIndex - 1);
        }
        else{
            return (char) ('a' + rowIndex - 27);
        }
    }

    public String toEncodedMap(){
        StringBuilder encodedMap = new StringBuilder();

        encodedMap.append("A " + indexToRow(players[0].row) + players[0].col);
        encodedMap.append(" B " + indexToRow(players[1].row) + players[1].col);
        for(int i = 0 ; i < items.size(); i++){
            encodedMap.append(" o" + i + " " + indexToRow(items.get(i).row) + items.get(i).col);
        }
        for(int i = 0 ; i < monsters.size(); i++){
            encodedMap.append(" m " + indexToRow(monsters.get(i).row) + monsters.get(i).col);
        }

        return encodedMap.toString();
    }

}
