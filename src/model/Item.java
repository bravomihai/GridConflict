package model;

public class Item {
    public int nr;
    public int row;
    public int col;
    public int dH;
    public int dA;
    public int dD;
    public int dS;

    public Item(int nr, int row, int col, int dH, int dA, int dD, int dS) {
        this.nr = nr;
        this.row = row;
        this.col = col;
        this.dH = dH;
        this.dA = dA;
        this.dD = dD;
        this.dS = dS;
    }
}
