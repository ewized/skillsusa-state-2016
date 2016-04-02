package auto;

import common.Commons;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

/** This is the drive class it contains the menu an the interface to interact with the car */
public final class Main {
    // Translations
    private static final String SEPARATE = "--------------------";
    private static final String INPUT_ERROR = "Invalid select use 1-%d";
    private static final String INPUT = "Select one: ";
    private static final String UNKNOWN = "unknown";
    private static final String MOVE_WHEELS = "Move the wheels";
    private static final String START_ENGINE = "Start the engine";
    private static final String OPEN_DOORS = "Open the doors";
    private static final String STOP_CAR = "Stop the car";
    private static final String END_PROGRAM = "End Program";
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
        try {
            if (fileWriter != null) {
                fileWriter.write(value + '\n');
            }
        } catch (IOException error) {
            System.out.println("Could not write to the file: " + error.getMessage());
        }
    }

    /** print the menu, return true for continue, false to close program */
    public static void menu() {
        List<MenuOption<Car>> items = generateMenu(car);
        int choice = 1, itemSize = items.size();
        for (MenuOption<Car> item : items) {
            System.out.println(String.format("[%d] %s", choice++, item.getItemName()));
        }

        System.out.print(INPUT);

        try {
            int value = in.nextInt() - 1;
            if (value < 0 || value > itemSize) {
                System.out.println(String.format(INPUT_ERROR, itemSize));
            }

            System.out.println(SEPARATE);
            items.get(value).processAction();
            System.out.println(SEPARATE);
        } catch (InputMismatchException | IllegalStateException | IndexOutOfBoundsException error) {
            System.out.println(String.format(INPUT_ERROR, itemSize));
            in = new Scanner(System.in);
        }
    }

    /** Generate the list of menu options with the instance of the car */
    public static List<MenuOption<Car>> generateMenu(Car car) {
        return Arrays.asList(
            new MenuOption<>(car, MOVE_WHEELS, Car::moveWheels),
            new MenuOption<>(car, START_ENGINE, Car::startEngine),
            new MenuOption<>(car, OPEN_DOORS, Car::openDoors),
            new MenuOption<>(car, STOP_CAR, Car::stopCar),
            new MenuOption<>(car, END_PROGRAM, arg -> System.exit(0))
        );
    }


    /** Create menu options for the menu */
    private static class MenuOption<T> {
        private T inst;
        private Consumer<T> action;
        private String itemName;

        /** Create the menu option, provide the inst for the action, the name of the action then the action */
        public MenuOption(T inst, String itemName, Consumer<T> action) {
            this.inst = Objects.requireNonNull(inst, "The instance must not be null");
            this.action = Objects.requireNonNull(action, "The action must not be null");
            this.itemName = (itemName == null) ? UNKNOWN : itemName;
        }

        /** Get the name of the item */
        public String getItemName() {
            return itemName;
        }

        /** Try to process the action, when the action fails prints the error to stderr */
        public void processAction() {
            try {
                action.accept(inst);
            } catch (Exception error) {
                // print error to system err avoid clutter of stdout
                error.printStackTrace(System.err);
            }
        }
    }
}
