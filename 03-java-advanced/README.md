# 📗 Module 03 — Java Advanced

> **Level:** 🟡 Intermediate | **Estimated Time:** 4–5 days

---

## 📚 Table of Contents
1. [Interfaces (Advanced)](#1-interfaces-advanced)
2. [Generics](#2-generics)
3. [Collections Framework](#3-collections-framework)
4. [Exception Handling](#4-exception-handling)
5. [Java 8+ Features](#5-java-8-features)
6. [Enums](#6-enums)
7. [Records (Java 16+)](#7-records-java-16)

---

## 1. Interfaces (Advanced)

```java
// Functional Interface — has exactly ONE abstract method
@FunctionalInterface
interface Greeting {
    String greet(String name);  // only one abstract method

    // default method — has implementation
    default void printGreeting(String name) {
        System.out.println(greet(name));
    }
}

// Lambda implements a functional interface
Greeting formal = name -> "Good morning, " + name + "!";
Greeting casual = name -> "Hey " + name + "!";

formal.printGreeting("Punit");  // Good morning, Punit!
casual.printGreeting("Punit");  // Hey Punit!
```

---

## 2. Generics

Generics allow you to write code that works with **any data type** while remaining type-safe.

```java
// Generic class
public class Box<T> {
    private T value;

    public Box(T value) { this.value = value; }
    public T getValue() { return value; }

    @Override
    public String toString() { return "Box[" + value + "]"; }
}

Box<String> strBox = new Box<>("Hello");
Box<Integer> intBox = new Box<>(42);
Box<Double> dblBox = new Box<>(3.14);

System.out.println(strBox.getValue()); // Hello
System.out.println(intBox.getValue()); // 42

// Generic method
public static <T extends Comparable<T>> T findMax(T a, T b) {
    return a.compareTo(b) >= 0 ? a : b;
}

System.out.println(findMax(10, 25));       // 25
System.out.println(findMax("apple", "mango")); // mango

// Wildcard
public static void printList(List<?> list) {
    for (Object item : list) System.out.println(item);
}
```

---

## 3. Collections Framework

```
Collection
├── List (ordered, allows duplicates)
│   ├── ArrayList   — fast random access
│   └── LinkedList  — fast insert/delete
├── Set (no duplicates)
│   ├── HashSet     — unordered
│   ├── LinkedHashSet — insertion order
│   └── TreeSet     — sorted order
└── Queue
    ├── LinkedList
    └── PriorityQueue

Map (key-value pairs)
├── HashMap        — unordered
├── LinkedHashMap  — insertion order
└── TreeMap        — sorted by key
```

### List — ArrayList
```java
import java.util.ArrayList;
import java.util.List;

List<String> fruits = new ArrayList<>();
fruits.add("Apple");
fruits.add("Banana");
fruits.add("Cherry");
fruits.add(1, "Mango");         // insert at index 1

System.out.println(fruits);             // [Apple, Mango, Banana, Cherry]
System.out.println(fruits.get(0));      // Apple
System.out.println(fruits.size());      // 4
System.out.println(fruits.contains("Banana")); // true

fruits.remove("Banana");               // remove by value
fruits.remove(0);                       // remove by index

// Iterate
for (String fruit : fruits) {
    System.out.println(fruit);
}
```

### Map — HashMap
```java
import java.util.HashMap;
import java.util.Map;

Map<String, Integer> scores = new HashMap<>();
scores.put("Punit", 95);
scores.put("Rahul", 88);
scores.put("Priya", 92);

System.out.println(scores.get("Punit"));         // 95
System.out.println(scores.containsKey("Rahul")); // true
System.out.println(scores.size());               // 3

scores.put("Punit", 98);  // update existing key
scores.remove("Rahul");   // remove a key

// Iterate over map
for (Map.Entry<String, Integer> entry : scores.entrySet()) {
    System.out.println(entry.getKey() + " -> " + entry.getValue());
}

// getOrDefault — safe access
int score = scores.getOrDefault("Unknown", 0);
```

### Set — HashSet
```java
import java.util.HashSet;
import java.util.Set;

Set<String> cities = new HashSet<>();
cities.add("Mumbai");
cities.add("Delhi");
cities.add("Mumbai");  // duplicate — ignored!

System.out.println(cities.size());           // 2
System.out.println(cities.contains("Delhi")); // true
```

---

## 4. Exception Handling

```java
// Checked Exception — must be caught or declared
public void readFile(String path) throws IOException {
    FileReader reader = new FileReader(path); // throws IOException
}

// Unchecked Exception — extends RuntimeException
public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

// try-catch-finally
try {
    int[] arr = new int[3];
    arr[10] = 5;  // ArrayIndexOutOfBoundsException
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Array error: " + e.getMessage());
} catch (Exception e) {
    System.out.println("General error: " + e.getMessage());
} finally {
    System.out.println("This ALWAYS runs — use for cleanup");
}

// Multi-catch (Java 7+)
try {
    // risky code
} catch (NullPointerException | IllegalArgumentException e) {
    System.out.println("Caught: " + e.getMessage());
}

// try-with-resources — auto closes resources
try (Scanner scanner = new Scanner(new File("data.txt"))) {
    while (scanner.hasNextLine()) {
        System.out.println(scanner.nextLine());
    }
} catch (FileNotFoundException e) {
    System.out.println("File not found!");
}
// scanner is automatically closed here
```

---

## 5. Java 8+ Features

### Lambda Expressions
```java
// Before Java 8 — anonymous class
Runnable r1 = new Runnable() {
    @Override
    public void run() { System.out.println("Running..."); }
};

// Java 8 — lambda
Runnable r2 = () -> System.out.println("Running...");

// With parameters
Comparator<String> comp = (a, b) -> a.compareTo(b);

// With block body
Comparator<Integer> compInt = (a, b) -> {
    System.out.println("Comparing " + a + " and " + b);
    return Integer.compare(a, b);
};
```

### Built-in Functional Interfaces
```java
import java.util.function.*;

// Predicate<T> — takes T, returns boolean
Predicate<String> isLong = s -> s.length() > 5;
System.out.println(isLong.test("Hi"));         // false
System.out.println(isLong.test("SpringBoot")); // true

// Function<T, R> — takes T, returns R
Function<String, Integer> strLength = s -> s.length();
System.out.println(strLength.apply("Hello")); // 5

// Consumer<T> — takes T, returns void
Consumer<String> printer = s -> System.out.println(">> " + s);
printer.accept("Hello World"); // >> Hello World

// Supplier<T> — takes nothing, returns T
Supplier<String> greeting = () -> "Hello, Punit!";
System.out.println(greeting.get()); // Hello, Punit!
```

### Stream API
Streams let you process collections in a **declarative, functional** way.

```java
import java.util.List;
import java.util.stream.*;

List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// filter — keep elements matching predicate
List<Integer> evens = numbers.stream()
    .filter(n -> n % 2 == 0)
    .collect(Collectors.toList());
System.out.println(evens); // [2, 4, 6, 8, 10]

// map — transform each element
List<String> asStrings = numbers.stream()
    .map(n -> "#" + n)
    .collect(Collectors.toList());
System.out.println(asStrings); // [#1, #2, ...]

// filter + map + collect
List<String> names = List.of("Punit", "Rahul", "Priya", "Ali", "Deepa");
List<String> longNames = names.stream()
    .filter(name -> name.length() > 4)
    .map(String::toUpperCase)       // method reference
    .sorted()
    .collect(Collectors.toList());
System.out.println(longNames); // [DEEPA, PUNIT, PRIYA, RAHUL]

// reduce — combine all elements
int sum = numbers.stream()
    .reduce(0, Integer::sum);
System.out.println("Sum: " + sum); // 55

// count, min, max
long count = numbers.stream().filter(n -> n > 5).count(); // 5
Optional<Integer> max = numbers.stream().max(Integer::compareTo); // 10

// forEach
names.stream()
    .filter(n -> n.startsWith("P"))
    .forEach(System.out::println); // Punit, Priya

// Collectors
Map<Integer, List<String>> byLength = names.stream()
    .collect(Collectors.groupingBy(String::length));
System.out.println(byLength); // {3=[Ali], 4=[...], 5=[Punit, Priya, Rahul, Deepa]}
```

### Optional
```java
import java.util.Optional;

// Wrap a value that might be null
Optional<String> opt1 = Optional.of("Hello");
Optional<String> opt2 = Optional.empty();
Optional<String> opt3 = Optional.ofNullable(null); // safe null wrap

System.out.println(opt1.isPresent());              // true
System.out.println(opt1.get());                    // Hello
System.out.println(opt2.orElse("Default"));        // Default
System.out.println(opt3.orElseGet(() -> "Computed")); // Computed

opt1.ifPresent(v -> System.out.println("Value: " + v)); // Value: Hello

// Chaining
Optional<Integer> length = opt1.map(String::length);
System.out.println(length.get()); // 5
```

---

## 6. Enums

```java
public enum TaskStatus {
    PENDING, IN_PROGRESS, COMPLETED, CANCELLED;

    public boolean isActive() {
        return this == PENDING || this == IN_PROGRESS;
    }
}

TaskStatus status = TaskStatus.IN_PROGRESS;
System.out.println(status);             // IN_PROGRESS
System.out.println(status.isActive());  // true
System.out.println(status.ordinal());   // 1 (position)
System.out.println(status.name());      // IN_PROGRESS

// Switch with enum
switch (status) {
    case PENDING -> System.out.println("Not started yet");
    case IN_PROGRESS -> System.out.println("Working on it!");
    case COMPLETED -> System.out.println("Done!");
    default -> System.out.println("Cancelled");
}

// Enum with fields and constructor
public enum Priority {
    LOW(1), MEDIUM(5), HIGH(10);

    private final int value;
    Priority(int value) { this.value = value; }
    public int getValue() { return value; }
}

System.out.println(Priority.HIGH.getValue()); // 10
```

---

## 7. Records (Java 16+)

Records are immutable data classes with auto-generated constructor, getters, `equals`, `hashCode`, and `toString`.

```java
// Traditional class with lots of boilerplate
public class PersonOld {
    private final String name;
    private final int age;
    // constructor, getters, equals, hashCode, toString...
}

// Record — same thing in ONE line!
public record Person(String name, int age) {}

Person p = new Person("Punit", 25);
System.out.println(p.name());     // Punit
System.out.println(p.age());      // 25
System.out.println(p);            // Person[name=Punit, age=25]

// Records are immutable — no setters!
// p.name = "Other"; // COMPILE ERROR

// Records are perfect for DTOs in Spring Boot!
public record StudentDTO(String name, String email, int age) {}
```

---

## ✅ Best Practices
- Use `List.of()`, `Map.of()` for immutable collections
- Prefer streams over manual loops for data processing
- Always handle exceptions at the right layer — don't swallow them
- Use `Optional` instead of returning `null` from methods
- Use Records for simple data carriers (DTOs)
- Use specific exception types, not just `Exception`

## ⚠️ Common Mistakes
- Calling `.get()` on Optional without checking `isPresent()` first
- Modifying a collection while iterating it (ConcurrentModificationException)
- Using raw types (`List` instead of `List<String>`)
- Forgetting streams are lazy — a terminal operation is needed!

## 💡 Key Takeaways
- Collections are the backbone of Java data management
- Stream API makes data processing clean and readable
- Lambdas + Functional Interfaces = powerful, concise code
- Optional eliminates NullPointerException
- Records make data classes effortless

---

➡️ **Next:** [Module 04 — Maven & Build Tools](../04-maven-and-build-tools/README.md)
