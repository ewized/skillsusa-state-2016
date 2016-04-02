package common;

import java.util.Objects;
import java.util.function.Consumer;

/** Create menu options for the menu */
public class MenuOption<T> {
    // translations
    private static final String UNKNOWN = "unknown";
    // instance vars
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
