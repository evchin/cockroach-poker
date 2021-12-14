public class Player {

    Hand hand;
    Hand collected;
    Position position;

    public Player(Position position) {
        this.position = position;
        if (position == Position.BOTTOM) hand = new Hand(position.getHandCoords(), false, position.getRotation(), true);
        else hand = new Hand(position.getHandCoords(), false, position.getRotation(), false);
        collected = new Hand(position.getCollectedCoords(), true, position.getRotation(), true);
    }

    public void addToHand(Motive[] motives) {
        for (Motive m : motives) {
            hand.addCard(m);
        }
    }

    public boolean hasLost() {
        return collected.isFourOfAKind() || hand.isEmpty();
    }

}
