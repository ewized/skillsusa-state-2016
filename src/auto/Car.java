package auto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** This is a model for a car, it will have its own engine, doors, and wheels */
public class Car {
    // The engine for this car
    private Engine engine = new Engine();
    // Creates a immutable list of wheels for this car
    private List<Wheel> wheels = Collections.unmodifiableList(Arrays.asList(
        new Wheel(1),
        new Wheel(2),
        new Wheel(3),
        new Wheel(4)
    ));
    // The immutable list of doors for this car
    private List<Door> doors = Collections.unmodifiableList(Arrays.asList(
        new Door(),
        new Door()
    ));

    /** Moves all the wheels for this car */
    public void moveWheels() {
        wheels.forEach(Wheel::move);
    }

    /** Start the engine for this car */
    public void startEngine() {
        engine.start();
    }

    /** Open all the doors of this car */
    public void openDoors() {
        doors.forEach(Door::open);
    }

    /** Will stop the car */
    public void stopCar() {
        wheels.forEach(Wheel::stop);
        engine.stop();
        doors.forEach(Door::close);
    }
}
