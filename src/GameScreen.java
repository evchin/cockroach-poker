import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameScreen extends Screen {

    Card card;
    JTextPane claim;
    JTextPane text;

    JButton next;
    JButton accept;
    JButton pass;
    JButton claimTrue;
    JButton claimFalse;
    JButton yes;
    JButton no;

    TwoRowHand claimSelection;

    GridBagConstraints cardGBC = Helpers.setupGBC(4,5,2,4);
    GridBagConstraints claimGBC = Helpers.setupGBC(5,9,2,1);
    GridBagConstraints nextGBC = Helpers.setupGBC(6,7,1,1);
    GridBagConstraints leftGBC = Helpers.setupGBC(6,7,1,1);
    GridBagConstraints rightGBC = Helpers.setupGBC(8,7,1,1);
    GridBagConstraints textGBC = Helpers.setupGBC(6,5,5,2);
    Coordinates claimSelectionCoords = new Coordinates(6, 7);

    final static int cardWidth = 200;
    final static int rotation = 0;

    public GameScreen() {
        super(Constants.GAME_SCREEN_FILEPATH);
        setupGBL();
        setupInputs();

        card = new Card(Motive.BACK, true, rotation, cardWidth, false);
        add(card, cardGBC);
        displayCard(Motive.BACK, true);
        displayTextPanes();
        displayPlayers();
        setNextVisible(true);
    }

    public void setClaimSelectionVisible(boolean visible) {
        for (Card c : claimSelection.hand) {
            c.setVisible(visible);
        }
    }

    public void setNextVisible(boolean visible) {
        next.setVisible(visible);
    }

    public void setAcceptPassVisible(boolean visible) {
        accept.setVisible(visible);
        pass.setVisible(visible);
    }

    public void setAcceptVisible(boolean visible) {
        accept.setVisible(visible);
    }

    public void setTrueFalseVisible(boolean visible) {
        claimTrue.setVisible(visible);
        claimFalse.setVisible(visible);
    }

    public void setYesNoVisible(boolean visible) {
        yes.setVisible(visible);
        no.setVisible(visible);
    }

    public void displayCard(Motive motive, boolean grey) {
        card.setMotive(motive);
        card.setGrey(grey);
        card.updateGraphics();
    }

    private void displayTextPanes() {
        text = new JTextPane();
        claim = new JTextPane();
        styleText(text);
        styleText(claim);
        claim.setAlignmentX(CENTER_ALIGNMENT);
        add(text, textGBC);
        add(claim, claimGBC);
    }

    private void setupInputs() {
        claimSelection = new TwoRowHand(claimSelectionCoords, false);
        for (Card c : claimSelection.hand) {
            c.setVisible(false);
            add(c, c.getGbc());
        }

        next = new JButton("next");
        styleButton(next, Constants.GREEN);
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Game.updateGameState();
            }
        });
        add(next, nextGBC);

        accept = new JButton("accept");
        styleButton(accept, Constants.GREEN);
        accept.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Game.setPlayerAccepts(true);
                Game.updateGameState();
            }
        });
        add(accept, leftGBC);

        pass = new JButton("pass");
        styleButton(pass, Constants.RED);
        pass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Game.setPlayerAccepts(false);
                Game.updateGameState();
            }
        });
        add(pass, rightGBC);

        claimTrue = new JButton("true");
        styleButton(claimTrue, Constants.GREEN);
        claimTrue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Game.claim == Game.proposed) Game.setPlayerCorrect(true);
                else Game.setPlayerCorrect(false);
                Game.updateGameState();
            }
        });
        add(claimTrue, leftGBC);

        claimFalse = new JButton("false");
        styleButton(claimFalse, Constants.RED);
        claimFalse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Game.claim == Game.proposed) Game.setPlayerCorrect(false);
                else Game.setPlayerCorrect(true);
                Game.updateGameState();
            }
        });
        add(claimFalse, rightGBC);

        yes = new JButton("yes");
        styleButton(yes, Constants.GREEN);
        yes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Game.setPlayerNewClaim(true);
                Game.updateGameState();
            }
        });
        add(yes, leftGBC);

        no = new JButton("no");
        styleButton(no, Constants.RED);
        no.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Game.setPlayerNewClaim(false);
                Game.updateGameState();
            }
        });
        add(no, rightGBC);
    }

    private void styleButton(JButton b, Color c) {
        b.setFont(Constants.MEDIUM_FONT);
        b.setBackground(c);
        b.setBorderPainted(false);
        b.setVisible(false);
    }

    private void styleText(JTextPane t) {
        t.setFont(Constants.MEDIUM_FONT);
        t.setOpaque(false);
        t.setEditable(false);
        t.setAlignmentY(TOP_ALIGNMENT);
        t.setAlignmentX(LEFT_ALIGNMENT);
        t.setForeground(Color.WHITE);
        t.setVisible(true);
    }

    private void setupGBL() {
        GridBagLayout gbl = new GridBagLayout();
        gbl.columnWeights = new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        gbl.rowWeights = new double[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        setLayout(gbl);
    }

    private void displayPlayers() {
        for (Player p : Game.players) {
            for (Card c : p.hand.getHand()) add(c, c.getGbc());
            for (Card c : p.collected.getHand()) add(c, c.getGbc());
        }
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public void setClaim(String claim) {
        this.claim.setText(claim);
    }
}
