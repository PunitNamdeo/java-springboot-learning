import java.util.Arrays;

/**
 * ArraysExample.java
 * Demonstrates array declaration, access, modification, iteration, and 2D arrays
 */
public class ArraysExample {

    public static void main(String[] args) {

        // ── DECLARING ARRAYS ─────────────────────────────────────────
        int[] ages = new int[5];              // all elements default to 0
        String[] names = new String[3];       // all elements default to null
        double[] prices = {9.99, 14.50, 3.75, 22.00};  // initialized with values

        // ── ACCESSING AND MODIFYING ───────────────────────────────────
        System.out.println("=== Accessing Array Elements ===");
        ages[0] = 25;
        ages[1] = 30;
        ages[2] = 22;
        ages[3] = 28;
        ages[4] = 35;

        System.out.println("First age:  " + ages[0]);      // 25
        System.out.println("Last age:   " + ages[ages.length - 1]); // 35
        System.out.println("Array size: " + ages.length);  // 5

        // ── ITERATING ────────────────────────────────────────────────
        System.out.println("\n=== Iterating with for loop ===");
        for (int i = 0; i < ages.length; i++) {
            System.out.println("ages[" + i + "] = " + ages[i]);
        }

        System.out.println("\n=== Iterating with for-each ===");
        for (double price : prices) {
            System.out.println("Price: $" + price);
        }

        // ── ARRAYS UTILITY CLASS ─────────────────────────────────────
        System.out.println("\n=== Arrays Utility ===");
        int[] numbers = {5, 3, 8, 1, 9, 2, 7};
        System.out.println("Before sort: " + Arrays.toString(numbers));
        Arrays.sort(numbers);
        System.out.println("After sort:  " + Arrays.toString(numbers));

        // Binary search (array must be sorted first)
        int index = Arrays.binarySearch(numbers, 8);
        System.out.println("Index of 8: " + index);

        // Fill array with a value
        int[] filled = new int[5];
        Arrays.fill(filled, 99);
        System.out.println("Filled:      " + Arrays.toString(filled)); // [99,99,99,99,99]

        // Copy array
        int[] copy = Arrays.copyOf(numbers, numbers.length);
        System.out.println("Copy:        " + Arrays.toString(copy));

        // ── 2D ARRAY ─────────────────────────────────────────────────
        System.out.println("\n=== 2D Array (Matrix) ===");
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                System.out.printf("%3d", matrix[row][col]);
            }
            System.out.println();
        }
        System.out.println("Element at row 1, col 2: " + matrix[1][2]); // 6
    }
}
