package model;

public class Move {
    public char type;
    public char row;
    public int col;

    public Move (char type, char row, int col) {
        this.type = type;
        this.row = row;
        this.col = col;
    }
}
