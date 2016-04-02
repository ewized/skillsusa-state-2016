package common;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public final class Commons {
    private Commons() {} // Util class
    // Translations
    public static final String SEPARATE = "--------------------";
    public static final String INPUT_ERROR = "Invalid select use 1-%d";
    public static final String INPUT = "Select one: ";
    public static final String END_PROGRAM = "End Program";
    public static final String ASK_ERROR = "Enter a correct input";

    /** Create the instance of a file writer and attach a shutdown hock for it */
    public static Optional<FileWriter> createFileWriter(String fileName) {
        FileWriter writerReturn = null;
        try {
            FileWriter writer = new FileWriter(new File(fileName));
            writerReturn = writer;
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    writer.close();
                } catch (IOException error) {
                    System.out.println("Could not close the stream: " + error.getMessage());
                }
            }));
        } catch (IOException error) {
            System.out.println("Problems opening the file: " + error.getMessage());
        }
        return Optional.ofNullable(writerReturn);
    }

    /** Print the string to the writer */
    public static void println(Writer writer, String line) {
        try {
            if (writer != null) {
                writer.write(line + '\n');
            }
        } catch (IOException error) {
            System.out.println("Could not write to the file: " + error.getMessage());
        }
    }

    /** Generate a menu, returns the Scanner or a new one when it fails */
    public static <T> Scanner menu(List<MenuOption<T>> items, Scanner in) {
        int choice = 1, itemSize = items.size();
        for (MenuOption<T> item : items) {
            System.out.println(String.format("[%d] %s", choice++, item.getItemName()));
        }

        System.out.print(Commons.INPUT);

        try {
            int value = in.nextInt() - 1;
            if (value < 0 || value > itemSize) {
                System.out.println(String.format(Commons.INPUT_ERROR, itemSize));
            }

            System.out.println(Commons.SEPARATE);
            items.get(value).processAction();
            System.out.println(Commons.SEPARATE);
        } catch (InputMismatchException | IllegalStateException | IndexOutOfBoundsException error) {
            System.out.println(String.format(Commons.INPUT_ERROR, itemSize));
            in = new Scanner(System.in);
        }

        return in;
    }
}
