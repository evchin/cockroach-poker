import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TwoRowHand extends Hand {

    public TwoRowHand(Coordinates coords, boolean grey) {
        super(coords, grey, 0, false);
    }

    @Override
    protected void setupCards() {
        for (int i = 0; i < Constants.NUM_MOTIVES; i++) {
            hand[i] = new Card(Motive.getMotive(i), grey, rotation, showAmount);
            GridBagConstraints gbc = new GridBagConstraints();

            if (i < 4) {
                gbc.gridx = coords.x + i;
                gbc.gridy = coords.y;
            }
            else {
                gbc.gridx = coords.x - (Constants.NUM_MOTIVES / 2) + i;
                gbc.gridy = coords.y + 1;
            }

            Motive m = hand[i].getMotive();
            hand[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (Game.gameState == GameState.PLAYER_CLAIM && rotation == 0) {
                        Game.claim = m;
                        Game.updateGameState();
                    }
                }
            });

            hand[i].setGbc(gbc);
        }
    }

}
