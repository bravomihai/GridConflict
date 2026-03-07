package util;

public class IntRange {
    private int min, max;

    public IntRange(int min, int col){
        this.min = min;
        this.max = col;
    }

    public int getMin() { return min;}
    public int getMax() { return max;}

    public void setMin(int min){ this.min = min; }
    public void setMax(int max){ this.max = max; }

}
