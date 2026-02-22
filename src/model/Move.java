package model;

public class Move {
    public char type;
    public char row;
    public int col;

    public static Move fromString(String s) {
        String[] parts = s.trim().split("\\s+");

        Move m = new Move();
        m.type = parts[0].charAt(0);
        m.row = parts[1].charAt(0);
        m.col = Integer.parseInt(parts[2]);

        return m;
    }
}
