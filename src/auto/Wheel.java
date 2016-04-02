package auto;

/** This represents a model for a wheel, it can move round and round like a record */
public class Wheel {
    private static final String MOVE = "Wheel %s is moving";
    private static final String STOP = "Wheel %s is stopped";
    private static final String[] ID_MAP = {"FlAT", "one", "two", "three", "four", "five", "six"};
    private int id;

    /** Create the wheel with the specific id, 1-6 are supported, or defaults to the dummy value */
    public Wheel(int id) {
        this.id = (id < 1 || id > ID_MAP.length) ? 0 : id;
    }

    /** Move the wheel */
    public void move() {
        System.out.println(String.format(MOVE, ID_MAP[id]));
    }

    /** Stop the wheel */
    public void stop() {
        System.out.println(String.format(STOP, ID_MAP[id]));
    }
}
