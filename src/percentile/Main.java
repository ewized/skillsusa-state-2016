package percentile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

/** This program will read the file located in the current working director , or from arg -Dfile_name */
public class Main {
    private static final String FILE_NAME = System.getProperty("file_name", "median.txt");


    public static void main(String[] args) {
        List<String> input = readInput();
        input.forEach(line -> {
            int[] elemetns = parseInput(line);
            outputPercentile(elemetns);
        });
    }

    /** Out put the percentile of the data set */
    public static void outputPercentile(int[] integers) {
        integers = Sorting.mergeSort(integers);
        double median = IntStream.of(integers).sum() / integers.length;
        double percentile = (median * 100) / integers.length;

        System.out.println(median + " - " + percentile);
    }

    /** Print the array for debugging purposes */
    public static void printArray(int[] array) {
        for (int item : array) {
            System.out.print(item + " ");
        }
        System.out.println();
    }

    /** Read the inputs from the input file */
    private static List<String> readInput() {
        File file = new File(FILE_NAME);
        List<String> lines = new ArrayList<>();

        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file))) {
            Scanner in = new Scanner(reader);
            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (line == null || line.isEmpty()) continue;
                lines.add(in.nextLine());
            }
        } catch (IOException error) {
            System.out.println(String.format("Could not find a file name by %s", FILE_NAME));
        }

        return lines;
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
}
