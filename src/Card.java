import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Card extends JButton {

    private GridBagConstraints gbc;
    private int amount = 0;

    private Motive motive;
    private boolean grey;
    private final boolean showAmount;
    private final int rotation;

    private final int width;
    private static final double h2wRatio = 3.5 / 2.5;
    private static final String filePath = "assets/";
    private static final String greyExtension = "-grey.png";
    private static final String normalExtension = ".png";

    public Card(Motive motive, boolean grey, int rotation, boolean showAmount) {
        this(motive, grey, rotation, 50, showAmount);
    }

    public Card(Motive motive, boolean grey, int rotation, int width, boolean showAmount) {
        super();
        this.motive = motive;
        this.rotation = rotation;
        this.grey = grey;
        this.width = width;
        this.showAmount = showAmount;
        gbc = new GridBagConstraints();
        setupGraphics();
    }

    public void incrementAmount() {
        amount++;
        updateText();
    }

    public void decrementAmount() {
        assert amount > 0 : "Cannot decrement an amount less than 1.";
        amount--;
        updateText();
    }

    @Override
    public String toString() {
        return "Card{" + "motive=" + motive + ", rotation=" + rotation + '}';
    }

    public GridBagConstraints getGbc() {
        return gbc;
    }

    public void setGbc(GridBagConstraints gbc) {
        this.gbc = gbc;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isEmpty() {
        return amount == 0;
    }

    private void setupGraphics() {
        Image img;

        try {
            img = ImageIO.read(getImageFile()).getScaledInstance(width, (int) (width * h2wRatio), Image.SCALE_SMOOTH);
            ImageIcon image = new ImageIcon(img);
            RotatedIcon ri = new RotatedIcon(image, rotation);
            setIcon(ri);
        } catch (IOException io) {
            System.out.println(io);
        }

        if (showAmount) {
            updateText();
            setFont(Constants.MEDIUM_FONT);
            setForeground(Constants.CARD_LABEL);
        }

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.CENTER;

        setBorderPainted(false);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setVisible(true);
    }

    public void updateGraphics() {
        Image img;
        try {
            img = ImageIO.read(getImageFile()).getScaledInstance(width, (int) (width * h2wRatio), Image.SCALE_SMOOTH);
            ImageIcon image = new ImageIcon(img);
            RotatedIcon ri = new RotatedIcon(image, rotation);
            setIcon(ri);
        } catch (IOException io) {
            System.out.println(io);
        }
    }

    private void updateText() {
        if (showAmount) setText(Integer.toString(amount));
    }

    private File getImageFile() {
        if (grey) { return new File(filePath + motive.getString() + greyExtension); }
        else { return new File(filePath + motive.getString() + normalExtension); }
    }

    public boolean getGrey() {
        return grey;
    }

    public Motive getMotive() {
        return motive;
    }

    public void setMotive(Motive motive) {
        this.motive = motive;
    }

    public void setGrey(boolean grey) {
        this.grey = grey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return motive == card.motive;
    }
}