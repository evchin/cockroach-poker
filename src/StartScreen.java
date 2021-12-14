import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen extends Screen {

    JButton startButton;
    JLabel title;

    public StartScreen() {
        super(Constants.START_SCREEN_FILEPATH);
        setupGBL();
        setupStartButton();
        setupTitle();
    }

    private void setupGBL() {
        GridBagLayout gbl = new GridBagLayout();
        gbl.rowWeights = new double[] {10, 80, 10};
        gbl.columnWeights = new double[] {10, 80, 10};
        setLayout(gbl);
    }

    private void setupStartButton() {
        startButton = new JButton("start");
        startButton.setFont(Constants.LARGE_FONT);
        startButton.setBackground(Constants.GREEN);
        startButton.setBorderPainted(false);
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Game.startScreen.setVisible(false);
                Game.gameScreen.setVisible(true);
            }
        });
        add(startButton, Helpers.setupGBC(2, 0, new Insets(75,0,0,100)));
    }

    private void setupTitle() {
        title = new JLabel("Cockroach Poker");
        title.setFont(Constants.LARGE_FONT);
        add(title, Helpers.setupGBC(0, 2, new Insets(0,50,15,0)));
    }
}