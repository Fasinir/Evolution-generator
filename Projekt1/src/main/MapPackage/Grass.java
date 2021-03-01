package MapPackage;

import AnimalAndMoving.Vector2d;

public class Grass {
    private Vector2d position;
    public Grass(Vector2d position)
    {
        this.position=position;
    }

    @Override
    public String toString()
    {
        return "*";
    }
    public Vector2d getPosition()
    {
        return this.position;
    }
}