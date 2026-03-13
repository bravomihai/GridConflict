package util;

import model.*;

public class BoardPrinter {

    public static void printBoard(GameState gs) {

        char[][] board = new char[gs.getHeight()][gs.getWidth()];

        for (int r = 0; r < gs.getHeight(); r++) {
            for (int c = 0; c < gs.getWidth(); c++) {
                board[r][c] = '.';
            }
        }

        for (Item it : gs.getItems()) {
            int r = it.row - 1;
            int c = it.col - 1;
            board[r][c] = (char) ('0' + it.nr);
        }

        for (Monster m : gs.getMonsters()) {
            int r = m.row - 1;
            int c = m.col - 1;
            board[r][c] = 'M';
        }

        board[gs.getPlayers().get(0).row - 1][gs.getPlayers().get(0).col - 1] = 'A';
        board[gs.getPlayers().get(1).row - 1][gs.getPlayers().get(1).col - 1] = 'B';

        Player pA = gs.getPlayers().get(0);
        Player pB = gs.getPlayers().get(1);

        char current = (gs.getPlayerIndex() == 0 ? 'A' : 'B');

        System.out.println("Current Player: " + current);
        System.out.println("A:" +
                " H=" + pA.H +
                " A=" + pA.A +
                " D=" + pA.D +
                " S=" + pA.s + "/" + pA.S);
        System.out.println("B:" +
                " H=" + pB.H +
                " A=" + pB.A +
                " D=" + pB.D +
                " S=" + pB.s + "/" + pB.S);

        System.out.print(" ");
        for (int c = 1; c <= gs.getWidth(); c++) {
            System.out.print((c / 10) % 10);
        }
        System.out.println();

        System.out.print(" ");
        for (int c = 1; c <= gs.getWidth(); c++) {
            System.out.print(c % 10);
        }
        System.out.println();

        for (int r = 0; r < gs.getHeight(); r++) {

            System.out.print(indexToRowChar(r));

            for (int c = 0; c < gs.getWidth(); c++) {
                System.out.print(board[r][c]);
            }

            System.out.println();
        }

        System.out.println(gs.toEncodedMap());

        System.out.println("===========\n");
    }

    private static char indexToRowChar(int idx) {
        if (idx < 26) return (char) ('A' + idx);
        return (char) ('a' + (idx - 26));
    }
}