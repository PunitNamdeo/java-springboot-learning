import java.util.*;
import java.util.stream.*;

/**
 * CollectionsExample.java
 * Demonstrates List, Set, Map and common operations
 */
public class CollectionsExample {

    public static void main(String[] args) {

        // --- ArrayList ---
        System.out.println("=== ArrayList ===");
        List<String> languages = new ArrayList<>(Arrays.asList("Java", "Python", "Go"));
        languages.add("Kotlin");
        languages.add(1, "Scala");  // insert at index 1
        System.out.println(languages);
        languages.remove("Go");
        Collections.sort(languages);
        System.out.println("Sorted: " + languages);

        // --- HashMap ---
        System.out.println("\n=== HashMap ===");
        Map<String, Integer> marks = new HashMap<>();
        marks.put("Punit", 95);
        marks.put("Rahul", 88);
        marks.put("Priya", 92);
        marks.put("Punit", 98);  // update
        System.out.println("Marks: " + marks);
        System.out.println("Punit's marks: " + marks.get("Punit"));
        marks.forEach((name, score) -> System.out.println(name + " -> " + score));

        // --- HashSet ---
        System.out.println("\n=== HashSet ===");
        Set<Integer> nums = new HashSet<>(Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6, 5));
        System.out.println("Unique numbers: " + nums);

        // --- TreeMap (sorted) ---
        System.out.println("\n=== TreeMap (sorted by key) ===");
        TreeMap<String, Integer> sorted = new TreeMap<>(marks);
        System.out.println(sorted);
    }
}
