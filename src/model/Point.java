package model;

public class Point {

    private final int r;
    private final int c;

    public Point(int r, int c){
        this.r = r;
        this.c = c;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Point)) return false;
        Point p = (Point) o;
        return r == p.r && c == p.c;
    }

    @Override
    public int hashCode(){
        return 31 * r + c;
    }
}