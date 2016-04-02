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
        String message = started ? START_MESSAGE_ERROR : START_MESSAGE;
        System.out.println(message);
        Main.println(message);
        if (!started) {
            started = true;
        }
    }

    /** Stops the car, when the car is stopped it %!?$s at you */
    public void stop() {
        String message = started ? STOP_MESSAGE : STOP_MESSAGE_ERROR;
        System.out.println(message);
        Main.println(message);
        if (started) {
            started = false;
        }
    }
}
