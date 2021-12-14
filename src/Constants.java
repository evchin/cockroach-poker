import java.awt.*;
import java.io.File;

public class Constants {

    public static final int SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final int SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 62;

    public static final int NUM_MOTIVES = 8;
    public static final int NUM_PLAYERS = 4;

    public static final int TOP_ROTATION = 180;
    public static final int LEFT_ROTATION = 90;
    public static final int RIGHT_ROTATION = 270;
    public static final int BOTTOM_ROTATION = 0;

    public static final Coordinates TOP_HAND_COORDS = new Coordinates(10, 1);
    public static final Coordinates TOP_COLLECTED_COORDS = new Coordinates(10, 2);
    public static final Coordinates LEFT_HAND_COORDS = new Coordinates(1, 3);
    public static final Coordinates LEFT_COLLECTED_COORDS = new Coordinates(2, 3);
    public static final Coordinates RIGHT_HAND_COORDS = new Coordinates(12, 10);
    public static final Coordinates RIGHT_COLLECTED_COORDS = new Coordinates(11, 10);
    public static final Coordinates BOTTOM_HAND_COORDS = new Coordinates(3, 12);
    public static final Coordinates BOTTOM_COLLECTED_COORDS = new Coordinates(3, 11);

    public static final String START_SCREEN_FILEPATH = "assets/start-screen.jpg";
    public static final String GAME_SCREEN_FILEPATH = "assets/game-screen.jpg";
    public static final String FONT_FILEPATH = "fonts/card-font.ttf";

    public static final Color GREEN = new Color(118,161,131);
    public static final Color RED = new Color(250,137,137);
    public static final Color CARD_LABEL = new Color(3,252,132);
    public static final float LARGE_SIZE = 50f;
    public static final float MEDIUM_SIZE = 35f;
    public static Font LARGE_FONT;
    public static Font MEDIUM_FONT;

    static {
        try {
            LARGE_FONT = Font.createFont(Font.TRUETYPE_FONT, new File(FONT_FILEPATH)).deriveFont(LARGE_SIZE);
            MEDIUM_FONT = Font.createFont(Font.TRUETYPE_FONT, new File(FONT_FILEPATH)).deriveFont(MEDIUM_SIZE);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(LARGE_FONT);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(MEDIUM_FONT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
