package util;

import model.GameState;

public class BoardPrinter {

    public static void printBoard(GameState gs) {


    }

    private static int rowCharToIndex(char c) {
        if (c >= 'A' && c <= 'Z') return c - 'A';
        if (c >= 'a' && c <= 'z') return (c - 'a') + 26;
        return -1;
    }

    private static char indexToRowChar(int idx) {
        if (idx < 26) return (char) ('A' + idx);
        return (char) ('a' + (idx - 26));
    }
}