package auto;

/** Represents a working door */
public class Door {
    // Translation messages
    private static final String DOOR_OPEN = "This door has opened";
    private static final String DOOR_OPEN_ERROR = "This door can not be open further, *hears the door break off*";
    private static final String DOOR_CLOSE = "This door has closed";
    private static final String DOOR_CLOSE_ERROR = "This door can not be closed further, *hears the gets crushed*";
    // Instance vars
    private boolean opened = false;

    /** Will try to open the door of the car */
    public void open() {
        System.out.println(opened ? DOOR_OPEN_ERROR : DOOR_OPEN);
        if (!opened) {
            opened = true;
        }
    }

    /** Will try to close the door */
    public void close() {
        System.out.println(opened ? DOOR_CLOSE : DOOR_CLOSE_ERROR);
        if (opened) {
            opened = false;
        }
    }
}
