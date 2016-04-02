package percentile;

import common.Commons;
import common.MenuOption;

import java.io.*;
import java.util.*;

/** This program will write the file from arg -Dfile_name or use the default */
public class Main {
    private static final String ASK_PERCENTILE = "Enter a percentile to look for: ";
    private static final String ASK_DATA_SET = "Enter a data set separated by commas: ";
    private static final String FILE_NAME = System.getProperty("file_name", "output.txt");
    private static FileWriter fileWriter;
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        fileWriter = Commons.createFileWriter(FILE_NAME).orElse(null);

        int percentile = askPercentile();
        int[] dataSet = askDataSet();

        Percentile data = new Percentile(percentile, dataSet);
        outputPercentile(data);
        while (true) menu(data);
    }

    /** Ask the user for the data set */
    public static int[] askDataSet() {
        String input = readInput(ASK_DATA_SET);
        int[] data =Sorting.mergeSort(parseInput(input));
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
            return Math.abs(Integer.parseInt(input));
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
            new MenuOption<>(percentile, "Change and view percentile", arg -> {
                in = new Scanner(System.in);
                arg.percentile = askPercentile();
                outputPercentile(arg);
            }),
            new MenuOption<>(percentile, "End Program", arg -> System.exit(0))
        );
    }

    /** Print the output to the file when the file exists */
    public static void println(String value) {
        Commons.println(fileWriter, value);
    }

    /** Out put the percentile of the data set */
    public static void outputPercentile(Percentile percentile) {
        int index = (int) Math.ceil((percentile.percentile * 100) % percentile.dataset.length);
        System.out.println(percentile.percentile + ": " + percentile.dataset[index]);
        println(String.valueOf(percentile.dataset[index]));
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

        return Arrays.copyOf(inputs, pos + 1);
    }

    /** A data to pass around the percentile for the menu system */
    private static class Percentile {
        private int percentile;
        private int[] dataset;

        public Percentile(int percentile, int[] dataset) {
            this.percentile = percentile;
            this.dataset = dataset;
        }
    }
}
