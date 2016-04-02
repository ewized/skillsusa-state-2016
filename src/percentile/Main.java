package percentile;

import common.Commons;
import common.MenuOption;

import java.io.*;
import java.util.*;

/** This program will write the file from arg -Dfile_name or use the default */
public class Main {
    // translations
    private static final String ASK_PERCENTILE = "Enter a percentile to look for: ";
    private static final String ASK_DATA_SET = "Enter a data set separated by commas: ";
    private static final String WHOLE_NUMBER = "Enter a whole number between 0-100";
    private static final String VIEW_PERCENTILE = "Change and view percentile";
    private static final String FILE_NAME = System.getProperty("file_name", "output.txt");
    // inst vars
    private static FileWriter fileWriter;
    private static Scanner in = new Scanner(System.in);

    /** The main method to rule them all */
    public static void main(String[] args) {
        fileWriter = Commons.createFileWriter(FILE_NAME).orElse(null);

        int percentile = askPercentile();
        int[] dataSet = askDataSet();

        Percentile data = new Percentile(percentile, dataSet);
        data.outputPercentile();

        while (true) {
            menu(data);
        }
    }

    /** Ask the user for the data set */
    public static int[] askDataSet() {
        String input = readInput(ASK_DATA_SET);
        int[] data = Sorting.mergeSort(parseInput(input));
        for (int i : data) {
            System.out.print(i + " ");
        }
        System.out.println();
        return data;
    }

    /** Ask the user for a percentile make sure its correct */
    public static int askPercentile() {
        try {
            String input = readInput(ASK_PERCENTILE);
            int number = Math.abs(Integer.parseInt(input));
            if (number < 0 || number > 100) {
                System.out.println(WHOLE_NUMBER);
                return askPercentile();
            }
            return number;
        } catch (NumberFormatException error) {
            System.out.println(Commons.ASK_ERROR);
            in = new Scanner(System.in);
            return askPercentile();
        }
    }

    /** Read the input from the user */
    public static String readInput(String message) {
        try {
            System.out.print(message);
            return in.nextLine();
        } catch (InputMismatchException | IllegalStateException error) {
            in = new Scanner(System.in);
            return readInput(message);
        }
    }

    /** The menu for the options */
    public static void menu(Percentile percentile) {
        in = Commons.menu(generateMenu(percentile), in);
    }

    /** Generate the list of menu options with the instance of the percentile */
    public static List<MenuOption<Percentile>> generateMenu(Percentile percentile) {
        return Arrays.asList(
            new MenuOption<>(percentile, VIEW_PERCENTILE, arg -> {
                in = new Scanner(System.in);
                arg.percentile = askPercentile();
                arg.outputPercentile();
            }),
            new MenuOption<>(percentile, Commons.END_PROGRAM, arg -> System.exit(0))
        );
    }

    /** Print the output to the file when the file exists */
    public static void println(String value) {
        Commons.println(fileWriter, value);
    }

    /** Gather the list of inputs from a string of numbers separated by a comma */
    private static int[] parseInput(String line) {
        String[] parts = line.split(",");
        int pos = 0;
        int[] inputs = new int[parts.length];

        for (String part : parts) {
            try {
                // Faster to check than filling stack trace
                if (part == null || part.isEmpty()) continue;
                inputs[pos++] = Integer.valueOf(part);
            } catch (NumberFormatException | IllegalStateException error) {} // do not need anything
        }

        return Arrays.copyOf(inputs, pos);
    }

    /** A data to pass around the percentile for the menu system */
    private static class Percentile {
        private int percentile;
        private int[] dataset;

        /** Create a percentile object */
        public Percentile(int percentile, int[] dataset) {
            this.percentile = percentile;
            this.dataset = dataset;
        }

        /** Out put the percentile of the data set */
        public void outputPercentile() {
            int index = (int) Math.ceil((percentile / 100.0) * dataset.length);
            index = index == 0 ? 0 : index - 1;
            System.out.println(String.format("percentile: %s%%, value: %s", percentile, dataset[index]));
            println(String.valueOf(dataset[index]));
        }
    }
}
