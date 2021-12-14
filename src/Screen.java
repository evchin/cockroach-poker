import javax.swing.*;
import java.awt.*;

public class Screen extends JPanel {

    Image background;

    protected Screen(String backgroundFilepath) {
        background = Toolkit.getDefaultToolkit().getImage(backgroundFilepath);
        setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, null);
    }

}
