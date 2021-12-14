import java.util.ArrayList;

public class Deck {

    private Motive[] cards;
    private int index = 0;
    private final int size = 64;

    public Deck() {
        cards = new Motive[size];
        shuffle();
    }

    public Motive[] deal(int numCards) {
        assert index + numCards <= size : "The deck will run out of cards if dealing " + numCards + " cards.";

        Motive[] dealt = new Motive[numCards];
        for (int i = 0; i < numCards; i++) {
            dealt[i] = cards[index++];
        }

        return dealt;
    }

    public int getSize() {
        return size;
    }

    private void shuffle() {
        final int maxValue = 8;
        int length = 8;
        int deckIndex = 0;

        // create arraylist of pairs of size length
        ArrayList<Pair> indices = new ArrayList<>();

        // initialize list with keys of 1-8 and values of 0
        for (int i = 0; i < length; i++) {
            indices.add(new Pair(i, 0));
        }

        while (length != 0 && deckIndex != size) {
            // get random number below length
            int rand = (int) (Math.random() * length);

            // get matching motive of arraylist[rand]
            Motive m = Motive.getMotive(indices.get(rand).getKey());

            // insert card into deck
            cards[deckIndex++] = m;

            // increment arraylist at random number
            int value = indices.get(rand).incrementValue();

            // if arraylist at random number is 4, remove item from list and decrement length
            if (value == maxValue) {
                indices.remove(rand);
                length--;
            }
        }
    }
}
