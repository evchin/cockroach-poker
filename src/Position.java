import java.util.HashMap;

public enum Position {

    LEFT(Constants.LEFT_HAND_COORDS, Constants.LEFT_COLLECTED_COORDS, Constants.LEFT_ROTATION),
    TOP(Constants.TOP_HAND_COORDS, Constants.TOP_COLLECTED_COORDS, Constants.TOP_ROTATION),
    RIGHT(Constants.RIGHT_HAND_COORDS, Constants.RIGHT_COLLECTED_COORDS, Constants.RIGHT_ROTATION),
    BOTTOM(Constants.BOTTOM_HAND_COORDS, Constants.BOTTOM_COLLECTED_COORDS, Constants.BOTTOM_ROTATION);

    final Coordinates handCoords;
    final Coordinates collectedCoords;
    final int rotation;
    static final HashMap<Integer, Position> map = new HashMap<>();

    Position(Coordinates handCoords, Coordinates collectedCoords, int rotation) {
        this.handCoords = handCoords;
        this.collectedCoords = collectedCoords;
        this.rotation = rotation;
    }

    public int getRotation() {
        return rotation;
    }

    public Coordinates getCollectedCoords() {
        return collectedCoords;
    }

    public Coordinates getHandCoords() {
        return handCoords;
    }

    static {
        for (Position p : Position.values()) {
            map.put(p.rotation, p);
        }
    }

}
