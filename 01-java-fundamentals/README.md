# 📘 Module 01 — Java Fundamentals

> **Level:** 🟢 Beginner | **Estimated Time:** 2–3 days

---

## 📚 Table of Contents
1. [What is Java?](#1-what-is-java)
2. [JDK vs JRE vs JVM](#2-jdk-vs-jre-vs-jvm)
3. [How Java Code Runs](#3-how-java-code-runs)
4. [Data Types](#4-data-types)
5. [Variables](#5-variables)
6. [Operators](#6-operators)
7. [Control Flow](#7-control-flow)
8. [Arrays](#8-arrays)
9. [Strings](#9-strings)
10. [Input & Output](#10-input--output)

---

## 1. What is Java?

Java is a **high-level, object-oriented, platform-independent** programming language created by James Gosling at Sun Microsystems in 1995.

**Key characteristics:**
- **Platform Independent** — Write once, run anywhere (WORA). Java code runs on any OS that has a JVM.
- **Object-Oriented** — Everything is modeled as objects.
- **Strongly Typed** — Every variable must have a declared type.
- **Automatic Memory Management** — Garbage Collector handles memory.

---

## 2. JDK vs JRE vs JVM

```
┌─────────────────────────────────────┐
│              JDK                    │  ← You install this to DEVELOP
│  ┌───────────────────────────────┐  │
│  │            JRE                │  │  ← Needed to RUN Java programs
│  │  ┌─────────────────────────┐  │  │
│  │  │         JVM             │  │  │  ← Executes bytecode
│  │  └─────────────────────────┘  │  │
│  │  + Java Libraries (rt.jar)    │  │
│  └───────────────────────────────┘  │
│  + Compiler (javac), Debugger, etc. │
└─────────────────────────────────────┘
```

| Term | Full Form | Purpose |
|------|-----------|--------|
| **JVM** | Java Virtual Machine | Executes Java bytecode (.class files) |
| **JRE** | Java Runtime Environment | JVM + standard libraries (to run programs) |
| **JDK** | Java Development Kit | JRE + compiler + tools (to develop programs) |

💡 **Key Takeaway:** Install JDK on your machine. It includes everything you need.

---

## 3. How Java Code Runs

```
You write:        HelloWorld.java   (source code)
     ↓
Compiler (javac): HelloWorld.class  (bytecode)
     ↓
JVM runs:         Output on screen  (platform specific execution)
```

```java
// Your first Java program
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

**Breaking it down:**
- `public class HelloWorld` — defines a class named HelloWorld
- `public static void main(String[] args)` — entry point of every Java program
- `System.out.println(...)` — prints text to the console

---

## 4. Data Types

Java has two categories of data types:

### Primitive Types (stored directly in memory)

| Type | Size | Default | Example |
|------|------|---------|--------|
| `byte` | 1 byte | 0 | `byte b = 100;` |
| `short` | 2 bytes | 0 | `short s = 1000;` |
| `int` | 4 bytes | 0 | `int age = 25;` |
| `long` | 8 bytes | 0L | `long population = 8000000000L;` |
| `float` | 4 bytes | 0.0f | `float pi = 3.14f;` |
| `double` | 8 bytes | 0.0d | `double price = 99.99;` |
| `boolean` | 1 bit | false | `boolean isActive = true;` |
| `char` | 2 bytes | '\u0000' | `char grade = 'A';` |

### Reference Types (store a reference/address to an object)
- String, Arrays, Classes, Interfaces

```java
String name = "Punit";       // reference type
int[] numbers = {1, 2, 3};   // array (reference type)
```

⚠️ **Common Mistake:** Using `float` without the `f` suffix causes a compile error.
```java
float price = 9.99;   // ❌ ERROR — 9.99 is a double by default
float price = 9.99f;  // ✅ CORRECT
```

---

## 5. Variables

```java
// Syntax: dataType variableName = value;
int age = 25;
String name = "Punit";
boolean isStudent = true;

// final = constant (value cannot change)
final double PI = 3.14159;

// var keyword (Java 10+) — type is inferred
var city = "Mumbai";  // compiler knows it's a String
var count = 42;       // compiler knows it's an int
```

**Types of Variables:**
- **Local variable** — inside a method, only accessible within that method
- **Instance variable** — inside a class but outside methods, belongs to each object
- **Static variable** — shared across all objects of a class

---

## 6. Operators

```java
// Arithmetic
int a = 10, b = 3;
System.out.println(a + b);   // 13
System.out.println(a - b);   // 7
System.out.println(a * b);   // 30
System.out.println(a / b);   // 3  (integer division!)
System.out.println(a % b);   // 1  (remainder/modulo)

// Comparison (returns boolean)
System.out.println(a > b);   // true
System.out.println(a == b);  // false
System.out.println(a != b);  // true

// Logical
boolean x = true, y = false;
System.out.println(x && y);  // false (AND)
System.out.println(x || y);  // true  (OR)
System.out.println(!x);      // false (NOT)

// Ternary — shorthand if/else
int max = (a > b) ? a : b;   // if a>b then max=a else max=b
System.out.println(max);     // 10

// Increment / Decrement
int count = 5;
count++;  // count becomes 6
count--;  // count becomes 5 again
```

---

## 7. Control Flow

### if / else if / else
```java
int score = 75;

if (score >= 90) {
    System.out.println("Grade: A");
} else if (score >= 75) {
    System.out.println("Grade: B");  // This prints
} else if (score >= 60) {
    System.out.println("Grade: C");
} else {
    System.out.println("Grade: F");
}
```

### switch
```java
String day = "MONDAY";
switch (day) {
    case "MONDAY":
    case "TUESDAY":
        System.out.println("Weekday");
        break;
    case "SATURDAY":
    case "SUNDAY":
        System.out.println("Weekend");
        break;
    default:
        System.out.println("Unknown");
}

// Modern switch expression (Java 14+)
String result = switch (day) {
    case "SATURDAY", "SUNDAY" -> "Weekend";
    default -> "Weekday";
};
```

### Loops
```java
// for loop
for (int i = 0; i < 5; i++) {
    System.out.println("i = " + i);  // 0,1,2,3,4
}

// while loop
int n = 1;
while (n <= 5) {
    System.out.println(n);
    n++;
}

// do-while (executes at least once)
int x = 10;
do {
    System.out.println(x);  // prints 10 even though condition is false
    x++;
} while (x < 5);

// enhanced for-each loop (for arrays/collections)
int[] numbers = {10, 20, 30, 40};
for (int num : numbers) {
    System.out.println(num);
}

// break and continue
for (int i = 0; i < 10; i++) {
    if (i == 3) continue;  // skip 3
    if (i == 7) break;     // stop at 7
    System.out.println(i); // prints 0,1,2,4,5,6
}
```

---

## 8. Arrays

```java
// Declaration and initialization
int[] ages = new int[5];           // array of 5 ints, default 0
int[] scores = {85, 92, 78, 95};   // array with values

// Accessing elements (index starts at 0)
System.out.println(scores[0]);  // 85
System.out.println(scores[3]);  // 95
System.out.println(scores.length); // 4

// Modifying elements
scores[1] = 100;

// Iterating
for (int i = 0; i < scores.length; i++) {
    System.out.println("Score " + i + ": " + scores[i]);
}

// 2D Array
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
};
System.out.println(matrix[1][2]);  // 6 (row 1, column 2)

// Useful Arrays utility
import java.util.Arrays;
Arrays.sort(scores);              // sort in ascending order
System.out.println(Arrays.toString(scores)); // [78, 85, 92, 100]
```

⚠️ **Common Mistake:** ArrayIndexOutOfBoundsException — accessing index that doesn't exist.
```java
int[] arr = {1, 2, 3};
System.out.println(arr[3]); // ❌ ERROR! Valid indices are 0, 1, 2
```

---

## 9. Strings

Strings in Java are **objects** (not primitives) and are **immutable** (cannot be changed).

```java
String name = "Punit Namdeo";

// Common String methods
System.out.println(name.length());           // 12
System.out.println(name.toUpperCase());      // PUNIT NAMDEO
System.out.println(name.toLowerCase());      // punit namdeo
System.out.println(name.charAt(0));          // P
System.out.println(name.indexOf("Namdeo")); // 6
System.out.println(name.substring(6));       // Namdeo
System.out.println(name.substring(0, 5));    // Punit
System.out.println(name.contains("Punit")); // true
System.out.println(name.replace("Punit", "Java")); // Java Namdeo
System.out.println(name.trim());             // removes leading/trailing spaces
System.out.println(name.isEmpty());          // false
System.out.println(name.startsWith("Punit")); // true
System.out.println(name.endsWith("eo"));     // true

// Splitting
String csv = "apple,banana,cherry";
String[] fruits = csv.split(",");
// fruits = ["apple", "banana", "cherry"]

// String comparison — ALWAYS use .equals(), never ==
String a = "hello";
String b = "hello";
System.out.println(a == b);       // might be true (but unreliable)
System.out.println(a.equals(b));  // ✅ always true
System.out.println(a.equalsIgnoreCase("HELLO")); // true

// String concatenation
String firstName = "Punit";
String lastName = "Namdeo";
String fullName = firstName + " " + lastName;  // "Punit Namdeo"

// StringBuilder — use when building strings in loops (much faster)
StringBuilder sb = new StringBuilder();
for (int i = 1; i <= 5; i++) {
    sb.append("item").append(i).append(", ");
}
System.out.println(sb.toString()); // item1, item2, item3, item4, item5,

// String.format
String msg = String.format("Hello, %s! You are %d years old.", "Punit", 25);
System.out.println(msg); // Hello, Punit! You are 25 years old.
```

⚠️ **Common Mistake:** Using `==` to compare Strings.

---

## 10. Input & Output

```java
import java.util.Scanner;

public class InputExample {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();  // reads a full line

        System.out.print("Enter your age: ");
        int age = scanner.nextInt();       // reads an integer

        System.out.print("Enter your GPA: ");
        double gpa = scanner.nextDouble(); // reads a decimal

        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("GPA: " + gpa);

        scanner.close(); // always close scanner when done
    }
}
```

---

## ✅ Best Practices
- Always use meaningful variable names (`studentAge` not `x`)
- Use `final` for constants and name them in UPPER_CASE (`final int MAX_SIZE = 100`)
- Use `StringBuilder` instead of `+` concatenation inside loops
- Always use `.equals()` to compare Strings, never `==`
- Close `Scanner` after use

## ⚠️ Common Mistakes
- Integer division: `10 / 3 = 3` not `3.33` — use `10.0 / 3` for decimal result
- Array index starts at 0, not 1
- `float` values need `f` suffix: `3.14f`
- `long` values need `L` suffix: `100000000L`

## 💡 Key Takeaways
- Java is strongly typed — every variable must have a declared type
- JVM makes Java platform-independent
- Strings are immutable objects — use StringBuilder for heavy string manipulation
- Arrays have fixed size — use ArrayList (Module 03) for dynamic sizing

---

➡️ **Next:** [Module 02 — Java OOP](../02-java-oop/README.md)
