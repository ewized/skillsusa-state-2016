package auto;

/** This represents a model for a wheel, it can move round and round like a record */
public class Wheel {
    // Translations
    private static final String MOVE = "Wheel %s is moving";
    private static final String STOP = "Wheel %s is stopped";
    private static final String[] ID_MAP = {"FlAT", "one", "two", "three", "four", "five", "six"};
    // Instance var
    private int id; // the id of the wheel

    /** Create the wheel with the specific id, 1-6 are supported, or defaults to the dummy value */
    public Wheel(int id) {
        this.id = (id < 1 || id > ID_MAP.length) ? 0 : id;
    }

    /** Move the wheel */
    public void move() {
        String message = String.format(MOVE, ID_MAP[id]);
        System.out.println(message);
        Main.println(message);
    }

    /** Stop the wheel */
    public void stop() {
        String message = String.format(STOP, ID_MAP[id]);
        System.out.println(message);
        Main.println(message);
    }
}
