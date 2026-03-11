package model;

public class EngineResult {
    public Move move;
    public int score;
    public double winChance;

    public EngineResult (Move move, int score, double winChance){
        this.move = move;
        this.score = score;
        this.winChance = winChance;
    }

    public EngineResult (String s){

        String[] parts = s.trim().split("\\s+");

        if(parts.length < 5)
            throw new IllegalArgumentException("Invalid engine result: " + s);

        this.move = new Move(
                parts[0].charAt(0),
                parts[1].charAt(0),
                Integer.parseInt(parts[2])
        );

        this.score = Integer.parseInt(parts[3]);
        this.winChance = Double.parseDouble(parts[4]);
    }

}
