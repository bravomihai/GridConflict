package util;

import model.GameState;

public class BoardPrinter {

    public static void printBoard(GameState gs) {

        char[][] board = new char[gs.H][gs.W];

        for (int i = 0; i < gs.H; i++) {
            for (int j = 0; j < gs.W; j++) {
                board[i][j] = '.';
            }
        }

        String[] tokens = gs.encodedMap.trim().split("\\s+");

        for (int i = 0; i < tokens.length - 1; i++) {

            String entity = tokens[i];
            String position = tokens[i + 1];

            if (entity.length() == 1 || entity.startsWith("o")) {

                char piece;
                if (entity.equals("A") || entity.equals("B") || entity.equals("m")) {
                    piece = entity.charAt(0);
                } else if (entity.startsWith("o")) {
                    piece = entity.charAt(1);
                } else {
                    continue;
                }

                char rowChar = position.charAt(0);
                int col = Integer.parseInt(position.substring(1));

                int row = rowCharToIndex(rowChar);
                int colIndex = col - 1;

                if (row >= 0 && row < gs.H && colIndex >= 0 && colIndex < gs.W) {
                    board[row][colIndex] = piece;
                }

                i++;
            }
        }

        System.out.println("current player: " + gs.currentPlayer);
        for(int i = 0 ; i < 2; i++){
            System.out.println( (char)('A' + i) + ": "
                    + gs.players[i].H + " "
                    + gs.players[i].A + " "
                    + gs.players[i].D + " "
                    + gs.players[i].s + " "
                    + gs.players[i].S);
        }

        System.out.print("  ");
        for (int c = 1; c <= gs.W; c++) {
            System.out.print(c);
        }
        System.out.println();

        for (int r = 0; r < gs.H; r++) {
            System.out.print(indexToRowChar(r) + " ");
            for (int c = 0; c < gs.W; c++) {
                System.out.print(board[r][c]);
            }
            System.out.println();
        }

        System.out.println();
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