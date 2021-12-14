import java.util.HashMap;

public enum Motive {

    ARROW(0),
    BOMB(1),
    BULLET(2),
    KNIFE(3),
    POISON(4),
    PUNCH(5),
    SKULL(6),
    SWORD(7),
    BACK(8);

    private final int value;
    private static final HashMap<Integer, Motive> map = new HashMap<>();
    private static final String[] strings = new String[]
            {"arrow", "bomb", "bullet", "knife", "poison", "punch", "skull", "sword", "back"};

    Motive(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getString() {
        return strings[value];
    }

    static {
        for (Motive motive : Motive.values()) {
            map.put(motive.value, motive);
        }
    }

    public static Motive getMotive(int value) {
        return map.get(value);
    }
}