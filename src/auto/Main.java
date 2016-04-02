package auto;

import java.util.*;
import java.util.function.Consumer;

public class Main {
    // Translations
    private static final String SEPERATE = "--------------------";
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
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        while(menu());
    }

    /** print the menu, return true for continue, false to close program */
    public static boolean menu() {
        List<MenuOption<Car>> items = generateMenu(car);
        int choice = 1, itemSize = items.size();
        for (MenuOption<Car> item : items) {
            System.out.println(String.format("[%d] %s", choice++, item.getItemName()));
        }

        boolean state = false;
        System.out.print(INPUT);

        try {
            int value = in.nextInt() - 1;
            if (value < 0 || value > itemSize) {
                System.out.println(String.format(INPUT_ERROR, itemSize));
            }

            System.out.println(SEPERATE);
            items.get(value).processAction();
            System.out.println(SEPERATE);
            state = true;
        } catch (InputMismatchException | IllegalStateException | IndexOutOfBoundsException error) {
            System.out.println(String.format(INPUT_ERROR, itemSize));
            in = new Scanner(System.in);
        }

        return state;
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
