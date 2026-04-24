import java.util.List;

/**
 * GenericsExample.java
 * Demonstrates generic classes, methods, and bounded types
 */
public class GenericsExample {

    public static void main(String[] args) {

        // Generic class
        System.out.println("=== Generic Box ===");
        Box<String> strBox = new Box<>("Hello Spring Boot");
        Box<Integer> intBox = new Box<>(100);
        Box<Double> dblBox = new Box<>(3.14);

        System.out.println(strBox);
        System.out.println(intBox);
        System.out.println(dblBox);

        // Generic method
        System.out.println("\n=== Generic Method ===");
        System.out.println("Max of 10, 25: " + findMax(10, 25));
        System.out.println("Max of 'apple', 'mango': " + findMax("apple", "mango"));
        System.out.println("Max of 3.5, 2.1: " + findMax(3.5, 2.1));

        // Wildcard
        System.out.println("\n=== Wildcard ===");
        printList(List.of(1, 2, 3));
        printList(List.of("a", "b", "c"));
    }

    // Generic method — T must be Comparable
    static <T extends Comparable<T>> T findMax(T a, T b) {
        return a.compareTo(b) >= 0 ? a : b;
    }

    // Wildcard — accepts list of any type
    static void printList(List<?> list) {
        System.out.println(list);
    }
}

// Generic class
class Box<T> {
    private T value;
    public Box(T value) { this.value = value; }
    public T getValue() { return value; }
    @Override public String toString() { return "Box[" + value + "] (" + value.getClass().getSimpleName() + ")"; }
}
