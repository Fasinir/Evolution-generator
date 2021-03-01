package AnimalAndMoving;

import java.util.Objects;

public class Vector2d {
    public int x;
    public int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }
    public String toString()
    {
        return "("+String.valueOf(this.x)+","+String.valueOf(this.y)+")";
    }
    public boolean precedes(Vector2d other)
    {
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other)
    {
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d upperRight(Vector2d other)
    {
        int x1;
        int y1;
        x1 = Math.max(this.x, other.x);
        y1 = Math.max(this.y, other.y);
        return new Vector2d(x1,y1);
    }
    public Vector2d lowerLeft(Vector2d other)
    {
        int x1;
        int y1;
        x1 = Math.min(this.x, other.x);
        y1 = Math.min(this.y, other.y);
        return new Vector2d(x1,y1);
    }
    public Vector2d add(Vector2d other)
    {
        return new Vector2d(this.x+other.x,this.y+other.y);
    }

    public Vector2d subtract(Vector2d other)
    {
        return new Vector2d(this.x-other.x,this.y-other.y);
    }

    @Override
    public boolean equals(Object o)
    {
        if(o instanceof Vector2d)
        {
            Vector2d other = (Vector2d) o;
            return (this.x==other.x && this.y==other.y);
        }
        else
        {
            return false;
        }
    }

    public Vector2d opposite()
    {
        return new Vector2d(this.x*(-1),this.y*(-1));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    //works if lowerbounds are 0
    public Vector2d moduloAdd(Vector2d other,int Topx,int Topy)//,int Bottomx, int Bottomy)
    {
        //using floorMod because I want the coordinates to be non-negative
        int x1=Math.floorMod(this.x+other.x,Topx);
        int y1=Math.floorMod(this.y+other.y,Topy);
        return new Vector2d(x1,y1);
    }

}
