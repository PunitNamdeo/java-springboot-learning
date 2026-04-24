import java.util.*;
import java.util.stream.*;
import java.util.function.*;

/**
 * LambdaAndStreamsExample.java
 * Demonstrates lambdas, functional interfaces, Stream API, and Optional
 */
public class LambdaAndStreamsExample {

    public static void main(String[] args) {

        // --- Lambdas ---
        System.out.println("=== Lambdas ===");
        Runnable r = () -> System.out.println("Hello from lambda!");
        r.run();

        Comparator<String> byLength = (a, b) -> Integer.compare(a.length(), b.length());
        List<String> words = Arrays.asList("banana", "kiwi", "apple", "fig");
        words.sort(byLength);
        System.out.println("Sorted by length: " + words);

        // --- Functional Interfaces ---
        System.out.println("\n=== Functional Interfaces ===");
        Predicate<Integer> isEven = n -> n % 2 == 0;
        Function<String, String> shout = s -> s.toUpperCase() + "!";
        Consumer<String> log = s -> System.out.println("[LOG] " + s);
        Supplier<List<String>> emptyList = ArrayList::new;

        System.out.println(isEven.test(4));       // true
        System.out.println(shout.apply("hello")); // HELLO!
        log.accept("Application started");         // [LOG] Application started
        System.out.println(emptyList.get());       // []

        // --- Stream API ---
        System.out.println("\n=== Stream API ===");
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // filter + map + collect
        List<Integer> evenSquares = numbers.stream()
            .filter(n -> n % 2 == 0)
            .map(n -> n * n)
            .collect(Collectors.toList());
        System.out.println("Even squares: " + evenSquares);

        // reduce
        int sum = numbers.stream().reduce(0, Integer::sum);
        System.out.println("Sum: " + sum);

        // count, min, max
        long countAbove5 = numbers.stream().filter(n -> n > 5).count();
        System.out.println("Count > 5: " + countAbove5);

        // Collectors.joining
        String joined = List.of("Java", "Spring", "Boot").stream()
            .collect(Collectors.joining(" + "));
        System.out.println("Joined: " + joined);

        // groupingBy
        List<String> names = List.of("Punit", "Priya", "Rahul", "Ram", "Ali");
        Map<Character, List<String>> grouped = names.stream()
            .collect(Collectors.groupingBy(n -> n.charAt(0)));
        System.out.println("Grouped: " + grouped);

        // --- Optional ---
        System.out.println("\n=== Optional ===");
        Optional<String> opt = Optional.ofNullable(null);
        System.out.println(opt.orElse("Default Value"));

        Optional<String> name = Optional.of("Punit");
        name.map(String::toUpperCase)
            .ifPresent(System.out::println);
    }
}
