package auto;

/** The engine model for a car, it can start and stop at demand */
public class Engine {
    // Message translations
    private static final String START_MESSAGE = "The engine has started";
    private static final String START_MESSAGE_ERROR = "The car is all ready on, *hears engine noise*";
    private static final String STOP_MESSAGE = "The engine has stopped";
    private static final String STOP_MESSAGE_ERROR = "The car is all ready off, *it does nothing*";
    // The instance vars
    private boolean started = false;

    /** Starts the car, when the car is all ready on its %$?!s at you */
    public void start() {
        System.out.println(started ? START_MESSAGE_ERROR : START_MESSAGE);
        if (!started) {
            started = true;
        }
    }

    /** Stops the car, when the car is stopped it %!?$s at you */
    public void stop() {
        System.out.println(started ? STOP_MESSAGE : STOP_MESSAGE_ERROR);
        if (started) {
            started = false;
        }
    }
}
