package AnimalAndMoving;

public interface AnimalPositionChangeObserver {
    void positionChanged(Vector2d oldPosition,Animal animal, Vector2d newPosition);
}
