import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.System.exit;

public class Hand {

    protected final Coordinates coords;
    protected final int rotation;
    protected final Card[] hand;
    protected final boolean grey;
    protected final boolean showAmount;

    public Hand(Coordinates coords, boolean grey, int rotation, boolean showAmount) {
        this.coords = coords;
        this.rotation = rotation;
        this.grey = grey;
        this.showAmount = showAmount;
        hand = new Card[Constants.NUM_MOTIVES];
        setupCards();
    }

    protected void setupCards() {
        for (int i = 0; i < Constants.NUM_MOTIVES; i++) {
            hand[i] = new Card(Motive.getMotive(i), grey, rotation, showAmount);
            GridBagConstraints gbc = new GridBagConstraints();

            // set up gbc
            switch (rotation) {
                case Constants.TOP_ROTATION -> {
                    gbc.gridx = coords.x - i;
                    gbc.gridy = coords.y;
                }
                case Constants.BOTTOM_ROTATION -> {
                    gbc.gridx = coords.x + i;
                    gbc.gridy = coords.y;
                }
                case Constants.LEFT_ROTATION -> {
                    gbc.gridy = coords.y + i;
                    gbc.gridx = coords.x;
                }
                case Constants.RIGHT_ROTATION -> {
                    gbc.gridy = coords.y - i;
                    gbc.gridx = coords.x;
                }
                default -> {
                    System.out.println("Reached end of switch statement for rotation.");
                    exit(1);
                }
            }

            Card c = hand[i];
            hand[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (Game.gameState == GameState.PLAYER_PROPOSE && rotation == 0 && c.getAmount() > 0 && !c.getGrey()) {
                        c.decrementAmount();
                        Game.proposed = c.getMotive();
                        Game.updateGameState();
                    }
                }
            });

            hand[i].setGbc(gbc);
        }
    }

    public Card[] getHand() {
        return hand;
    }

    public void removeCard(Motive motive) {
        hand[motive.getValue()].decrementAmount();
    }

    public Motive removeRandomCard() {
        int index;
        do index = (int) (Math.random() * Constants.NUM_MOTIVES);
        while (hand[index].getAmount() == 0);

        Motive m = Motive.getMotive(index);
        removeCard(m);
        return m;
    }

    public void addCard(Motive motive) {
        hand[motive.getValue()].incrementAmount();
    }

    boolean isFourOfAKind() {
        for (Card card : hand) {
            if (card.getAmount() >= 4) { return true; }
        }
        return false;
    }

    boolean isEmpty() {
        for (Card card : hand) {
            if (!card.isEmpty()) { return false; }
        }
        return true;
    }

}
