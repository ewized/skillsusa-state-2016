package auto;

import common.Commons;
import common.MenuOption;

import java.io.FileWriter;
import java.util.*;

/** This is the drive class it contains the menu an the interface to interact with the car */
public final class Main {
    // Translations
    private static final String MOVE_WHEELS = "Move the wheels";
    private static final String START_ENGINE = "Start the engine";
    private static final String OPEN_DOORS = "Open the doors";
    private static final String STOP_CAR = "Stop the car";
    // The car used for this program
    private static final Car car = new Car();
    private static final String FILE_NAME = System.getProperty("file_name", "output.txt");
    private static FileWriter fileWriter;
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        // Create the file writer
        fileWriter = Commons.createFileWriter(FILE_NAME).orElse(null);
        // The menu forever
        while (true) {
            menu();
        }
    }

    /** Print the output to the file when the file exists */
    public static void println(String value) {
        Commons.println(fileWriter, value);
    }

    /** print the menu, return true for continue, false to close program */
    public static void menu() {
        in = Commons.menu(generateMenu(car), in);
    }

    /** Generate the list of menu options with the instance of the car */
    public static List<MenuOption<Car>> generateMenu(Car car) {
        return Arrays.asList(
            new MenuOption<>(car, MOVE_WHEELS, Car::moveWheels),
            new MenuOption<>(car, START_ENGINE, Car::startEngine),
            new MenuOption<>(car, OPEN_DOORS, Car::openDoors),
            new MenuOption<>(car, STOP_CAR, Car::stopCar),
            new MenuOption<>(car, Commons.END_PROGRAM, arg -> System.exit(0))
        );
    }
}
