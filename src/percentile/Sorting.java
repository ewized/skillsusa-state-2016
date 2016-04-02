package percentile;

import java.util.Arrays;

/** Contains sorting algorithms for sorting */
public final class Sorting {
    private Sorting() {} // Util class

    /** A bubble sort to sort integers */
    public static int[] bubbleSort(int[] array) {
        boolean sorted;
        do {
            sorted = true;
            for (int i = 1; i < array.length; i++) {
                int a = array[i - 1];
                int b = array[i];
                if (a > b) {
                    array[i - 1] = b;
                    array[i] = a;
                    sorted = false;
                }
            }
        } while (!sorted);
        return array;
    }

    /** Merge sort an array of integers */
    public static int[] mergeSort(int[] array) {
        if (array.length <= 1) {
            return array;
        } else if (array.length == 2) { // Do the sort here for better performance
            return (array[1] < array[0]) ? new int[] {array[1], array[0]} : array;
        }
        // Split the data set in to two parts
        int middle = array.length / 2;
        int[] left = Arrays.copyOfRange(array, 0, middle);
        int[] right = Arrays.copyOfRange(array, middle, array.length);
        return mergeSort(mergeSort(left), mergeSort(right));
    }

    /** Internal use for the mergeSort */
    private static int[] mergeSort(int[] left, int[] right) {
        int[] merged = new int[left.length + right.length];
        int l = 0, r = 0; // l = left index | r = right index
        // Sort the data that we have
        while (l < left.length && r < right.length) {
            merged[l + r] = (left[l] > right[r]) ? right[r++] : left[l++];
        }
        // Copy the rest of the data if there is
        System.arraycopy(left, l, merged, l + r, left.length - l);
        System.arraycopy(right, r, merged, l + r, right.length - r);

        return merged;
    }
}
