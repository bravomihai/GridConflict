package util;

import model.*;

public class BoardPrinter {

    public static void printBoard(GameState gs) {

        char[][] board = new char[gs.H][gs.W];

        for(int r = 0; r < gs.H; r++){
            for(int c = 0; c < gs.W; c++){
                board[r][c] = '.';
            }
        }

        // items
        for(int i = 0; i < gs.items.size(); i++){
            Item it = gs.items.get(i);
            int r = it.row - 1;
            int c = it.col - 1;
            board[r][c] = (char)('0' + it.nr);
        }

        // monsters
        for(Monster m : gs.monsters){
            int r = m.row - 1;
            int c = m.col - 1;
            board[r][c] = 'M';
        }

        // players
        board[gs.players[0].row - 1][gs.players[0].col - 1] = 'A';
        board[gs.players[1].row - 1][gs.players[1].col - 1] = 'B';

        //stats

        Player pA = gs.players[0];
        Player pB = gs.players[1];

        char current = (gs.playerIndex == 0 ? 'A' : 'B');

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

        // tens line
        System.out.print(" ");
        for(int c = 1; c <= gs.W; c++){
            System.out.print((c / 10) % 10);
        }
        System.out.println();

        // units line
        System.out.print(" ");
        for(int c = 1; c <= gs.W; c++){
            System.out.print(c % 10);
        }
        System.out.println();

        // board
        for(int r = 0; r < gs.H; r++){

            System.out.print(indexToRowChar(r));

            for(int c = 0; c < gs.W; c++){
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