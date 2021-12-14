import java.awt.*;

public class Helpers {

    public static GridBagConstraints setupGBC(int gridx, int gridy, Insets insets) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.insets = insets;
        return gbc;
    }

    public static GridBagConstraints setupGBC(int gridx, int gridy, int gridWidth, int gridHeight) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridWidth;
        gbc.gridheight = gridHeight;
        gbc.fill = GridBagConstraints.BOTH;
        return gbc;
    }

    public static boolean flipCoin() {
        return Math.random() < 0.5;
    }

    public static Motive getRandomMotive() {
        return Motive.getMotive((int)(Math.random() * Constants.NUM_MOTIVES));
    }

}
