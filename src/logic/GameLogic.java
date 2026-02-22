package logic;

import model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameLogic {

    public static void applyMove(GameState gs, Move move) {
        if (gs == null || move == null) return;

        List<String> tokens = tokenize(gs.encodedMap);

        String cpEntity = String.valueOf(gs.currentPlayer);
        String opEntity = gs.currentPlayer == 'A' ? "B" : "A";

        int cpIdx = findEntityIndex(tokens, cpEntity);
        if (cpIdx == -1) return;

        Player cp = gs.players[(gs.currentPlayer == 'A') ? 0 : 1];
        Player op = gs.players[(gs.currentPlayer == 'A') ? 1 : 0];

        String oldPosToken = safeGet(tokens, cpIdx + 1);
        Position oldPos = parsePosToken(oldPosToken);

        if (move.type == 'p') {
            gs.consecutivePassRounds++;
            switchTurn(gs);
            return;
        }

        if (move.type == 'm') {
            gs.consecutivePassRounds = 0;
            Position newPos = new Position(move.row, move.col);
            int dist = manhattan(oldPos, newPos);
            cp.s = Math.max(0, cp.s - dist);

            tokens.set(cpIdx + 1, posToToken(newPos));

            int objIdx = findObjectIndexAtPosition(tokens, newPos);
            if (objIdx != -1) {
                String objToken = tokens.get(objIdx);
                int itemIndex = parseObjectIndex(objToken);
                if (itemIndex >= 0 && itemIndex < gs.items.size()) {
                    Item it = gs.items.get(itemIndex);
                    cp.H += it.dH;
                    cp.A += it.dA;
                    cp.D += it.dD;
                    cp.S += it.dS;
                }
                removeTokenPair(tokens, objIdx);
            }

            gs.encodedMap = joinTokens(tokens);
            return;
        }

        if (move.type == 'a') {
            gs.consecutivePassRounds = 0;
            cp.s = Math.max(0, cp.s - 10);
            Position targetPos = new Position(move.row, move.col);

            int oppIdxAtPos = findSpecificEntityAtPosition(tokens, opEntity, targetPos);
            if (oppIdxAtPos != -1) {
                int damage = Math.max(0, cp.A - op.D);
                op.H -= damage;
                gs.encodedMap = joinTokens(tokens);
                return;
            }

            int monsterIdx = findSpecificEntityAtPosition(tokens, "m", targetPos);
            if (monsterIdx != -1) {
                removeTokenPair(tokens, monsterIdx);
                cp.H += 10;
                gs.encodedMap = joinTokens(tokens);
                return;
            }

            gs.encodedMap = joinTokens(tokens);
        }
    }

    private static List<String> tokenize(String s) {
        if (s == null) return new ArrayList<>();
        String trimmed = s.trim();
        if (trimmed.isEmpty()) return new ArrayList<>();
        String[] arr = trimmed.split("\\s+");
        return new ArrayList<>(Arrays.asList(arr));
    }

    private static String joinTokens(List<String> tokens) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String t : tokens) {
            if (t == null || t.isEmpty()) continue;
            if (!first) sb.append(' ');
            sb.append(t);
            first = false;
        }
        return sb.toString();
    }

    private static int findEntityIndex(List<String> tokens, String entity) {
        if (tokens == null || entity == null) return -1;
        for (int i = 0; i + 1 < tokens.size(); i += 2) {
            if (entity.equals(tokens.get(i))) return i;
        }
        return -1;
    }

    private static int findObjectIndexAtPosition(List<String> tokens, Position pos) {
        if (tokens == null) return -1;
        String posTok = posToToken(pos);
        for (int i = 0; i + 1 < tokens.size(); i += 2) {
            String ent = tokens.get(i);
            String p = tokens.get(i + 1);
            if (ent != null && ent.startsWith("o") && p != null && p.equals(posTok)) return i;
        }
        return -1;
    }

    private static int findSpecificEntityAtPosition(List<String> tokens, String entity, Position pos) {
        if (tokens == null) return -1;
        String posTok = posToToken(pos);
        for (int i = 0; i + 1 < tokens.size(); i += 2) {
            String ent = tokens.get(i);
            String p = tokens.get(i + 1);
            if (ent != null && ent.equals(entity) && p != null && p.equals(posTok)) return i;
        }
        return -1;
    }

    private static void removeTokenPair(List<String> tokens, int i) {
        if (tokens == null) return;
        if (i < 0 || i + 1 >= tokens.size()) return;
        tokens.remove(i + 1);
        tokens.remove(i);
    }

    private static int parseObjectIndex(String objToken) {
        if (objToken == null || !objToken.startsWith("o")) return -1;
        String num = objToken.substring(1);
        try {
            return Integer.parseInt(num);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static String posToToken(Position p) {
        if (p == null) return "";
        return "" + p.row + p.col;
    }

    private static Position parsePosToken(String tok) {
        if (tok == null || tok.length() == 0) return new Position('A', 1);
        char row = tok.charAt(0);
        int col = 1;
        if (tok.length() > 1) {
            try {
                col = Integer.parseInt(tok.substring(1));
            } catch (NumberFormatException e) {
                col = 1;
            }
        }
        return new Position(row, col);
    }

    private static String safeGet(List<String> tokens, int idx) {
        if (tokens == null) return null;
        if (idx < 0 || idx >= tokens.size()) return null;
        return tokens.get(idx);
    }

    private static int manhattan(Position a, Position b) {
        return Math.abs(rowCharToIndex(a.row) - rowCharToIndex(b.row))
                + Math.abs(a.col - b.col);
    }

    private static void switchTurn(GameState gs) {
        int pidx = (gs.currentPlayer == 'A') ? 0 : 1;
        gs.players[pidx].s = gs.players[pidx].S;
        gs.currentPlayer = (gs.currentPlayer == 'A') ? 'B' : 'A';
    }

    private static int rowCharToIndex(char c) {
        if (c >= 'A' && c <= 'Z') return c - 'A';
        if (c >= 'a' && c <= 'z') return (c - 'a') + 26;
        return -1;
    }

    private static class Position {
        char row;
        int col;

        Position(char r, int c) {
            this.row = r;
            this.col = c;
        }
    }
}