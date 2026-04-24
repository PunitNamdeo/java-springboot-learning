/**
 * DataTypesExample.java
 * Demonstrates all Java primitive and reference data types
 */
public class DataTypesExample {

    public static void main(String[] args) {

        // ── PRIMITIVE TYPES ──────────────────────────────────────────

        byte byteVal = 127;                  // -128 to 127
        short shortVal = 32000;              // -32,768 to 32,767
        int intVal = 2_000_000;              // underscore for readability
        long longVal = 8_000_000_000L;       // L suffix required
        float floatVal = 3.14f;              // f suffix required
        double doubleVal = 3.141592653589793;
        boolean boolVal = true;
        char charVal = 'J';                  // single quotes for char

        System.out.println("=== Primitive Data Types ===");
        System.out.println("byte:    " + byteVal);
        System.out.println("short:   " + shortVal);
        System.out.println("int:     " + intVal);
        System.out.println("long:    " + longVal);
        System.out.println("float:   " + floatVal);
        System.out.println("double:  " + doubleVal);
        System.out.println("boolean: " + boolVal);
        System.out.println("char:    " + charVal);

        // ── REFERENCE TYPES ─────────────────────────────────────────

        String text = "Hello, Java!";        // String is a class
        int[] numbers = {10, 20, 30};        // array is a reference type

        System.out.println("\n=== Reference Types ===");
        System.out.println("String: " + text);
        System.out.println("Array:  " + numbers[0] + ", " + numbers[1] + ", " + numbers[2]);

        // ── TYPE CASTING ─────────────────────────────────────────────

        // Implicit (widening) — smaller to larger, automatic
        int myInt = 100;
        long myLong = myInt;      // int → long (automatic)
        double myDouble = myInt;  // int → double (automatic)

        // Explicit (narrowing) — larger to smaller, manual cast
        double pi = 3.99;
        int piInt = (int) pi;     // loses decimal part → 3

        System.out.println("\n=== Type Casting ===");
        System.out.println("int to long:   " + myLong);
        System.out.println("int to double: " + myDouble);
        System.out.println("double to int: " + piInt + " (decimal lost!)");

        // ── CONSTANTS ────────────────────────────────────────────────
        final double TAX_RATE = 0.18;   // cannot be changed after declaration
        final int MAX_RETRIES = 3;

        System.out.println("\n=== Constants ===");
        System.out.println("TAX_RATE:    " + TAX_RATE);
        System.out.println("MAX_RETRIES: " + MAX_RETRIES);
    }
}
