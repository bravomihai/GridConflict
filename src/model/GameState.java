package model;

import java.util.*;

public class GameState {

    public int consecutivePassRounds = 0;
    public int H;
    public int W;
    public char currentPlayer;

    public Player[] players = new Player[2];
    public List<Item> items = new ArrayList<>();

    public String encodedMap;

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

        sb.append(encodedMap);

        return sb.toString();
    }
}
