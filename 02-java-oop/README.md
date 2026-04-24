# 📗 Module 02 — Java OOP (Object-Oriented Programming)

> **Level:** 🟢 Beginner | **Estimated Time:** 3–4 days

---

## 📚 Table of Contents
1. [What is OOP?](#1-what-is-oop)
2. [Class & Object](#2-class--object)
3. [Encapsulation](#3-encapsulation)
4. [Inheritance](#4-inheritance)
5. [Polymorphism](#5-polymorphism)
6. [Abstraction](#6-abstraction)
7. [Access Modifiers](#7-access-modifiers)
8. [Static Members](#8-static-members)
9. [Object Class Methods](#9-object-class-methods)

---

## 1. What is OOP?

Object-Oriented Programming is a programming paradigm that organizes code around **objects** (data + behavior) rather than just functions.

**4 Pillars of OOP:**
```
┌──────────────────────────────────────────────┐
│  1. Encapsulation  → hide internal details    │
│  2. Inheritance    → reuse code from parent   │
│  3. Polymorphism   → one interface, many forms│
│  4. Abstraction    → hide complexity          │
└──────────────────────────────────────────────┘
```

---

## 2. Class & Object

A **Class** is a blueprint. An **Object** is an instance of that blueprint.

```java
// CLASS — the blueprint
public class Car {
    // Fields (attributes/state)
    String brand;
    String color;
    int speed;

    // Constructor — called when object is created
    public Car(String brand, String color) {
        this.brand = brand;   // 'this' refers to current object
        this.color = color;
        this.speed = 0;
    }

    // Methods (behavior)
    public void accelerate(int amount) {
        speed += amount;
        System.out.println(brand + " accelerated to " + speed + " km/h");
    }

    public void brake() {
        speed = 0;
        System.out.println(brand + " stopped.");
    }
}

// OBJECT — the instance
Car car1 = new Car("Toyota", "Red");
Car car2 = new Car("BMW", "Blue");

car1.accelerate(60);  // Toyota accelerated to 60 km/h
car2.accelerate(100); // BMW accelerated to 100 km/h
car1.brake();         // Toyota stopped.
```

### Constructor Types
```java
public class Student {
    String name;
    int age;

    // No-arg constructor
    public Student() {
        this.name = "Unknown";
        this.age = 0;
    }

    // Parameterized constructor
    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Constructor chaining with this()
    public Student(String name) {
        this(name, 18);  // calls the 2-arg constructor
    }
}
```

---

## 3. Encapsulation

**Definition:** Wrapping data (fields) and methods together, and hiding internal details using access modifiers.

**Why?** Prevents outside code from directly modifying internal state in unexpected ways.

```java
public class BankAccount {
    // private — cannot be accessed directly from outside
    private String accountNumber;
    private double balance;
    private String owner;

    public BankAccount(String owner, double initialBalance) {
        this.owner = owner;
        this.balance = initialBalance;
    }

    // PUBLIC GETTER — controlled read access
    public double getBalance() {
        return balance;
    }

    public String getOwner() {
        return owner;
    }

    // PUBLIC SETTER — controlled write access with validation
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive!");
            return;
        }
        balance += amount;
        System.out.println("Deposited: " + amount + " | New balance: " + balance);
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient funds!");
            return;
        }
        balance -= amount;
        System.out.println("Withdrawn: " + amount + " | New balance: " + balance);
    }
}

// Usage
BankAccount account = new BankAccount("Punit", 1000.0);
account.deposit(500);    // ✅ Deposited: 500.0 | New balance: 1500.0
account.withdraw(2000);  // ✅ Insufficient funds!
// account.balance = 99999; // ❌ COMPILE ERROR — private!
```

💡 **Key Takeaway:** Make fields `private`, expose them via `public` getters/setters. This gives you full control over how data is accessed and modified.

---

## 4. Inheritance

**Definition:** A child class inherits fields and methods from a parent class. Promotes code reuse.

```java
// PARENT class
public class Animal {
    String name;
    int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void eat() {
        System.out.println(name + " is eating.");
    }

    public void sleep() {
        System.out.println(name + " is sleeping.");
    }

    public String describe() {
        return "Animal: " + name + ", Age: " + age;
    }
}

// CHILD class — extends Animal
public class Dog extends Animal {
    String breed;

    public Dog(String name, int age, String breed) {
        super(name, age);  // calls parent constructor
        this.breed = breed;
    }

    // NEW method specific to Dog
    public void fetch() {
        System.out.println(name + " fetches the ball!");
    }

    // OVERRIDING parent method
    @Override
    public String describe() {
        return super.describe() + ", Breed: " + breed;  // super calls parent method
    }
}

// ANOTHER child class
public class Cat extends Animal {
    boolean isIndoor;

    public Cat(String name, int age, boolean isIndoor) {
        super(name, age);
        this.isIndoor = isIndoor;
    }

    public void purr() {
        System.out.println(name + " purrs...");
    }

    @Override
    public String describe() {
        return super.describe() + ", Indoor: " + isIndoor;
    }
}

// Usage
Dog dog = new Dog("Rex", 3, "Labrador");
dog.eat();      // Inherited from Animal
dog.fetch();    // Dog's own method
System.out.println(dog.describe()); // Animal: Rex, Age: 3, Breed: Labrador

Cat cat = new Cat("Whiskers", 2, true);
cat.sleep();    // Inherited from Animal
cat.purr();     // Cat's own method
```

### The `final` keyword
```java
final class CannotExtend { }      // no class can extend this
// class Child extends CannotExtend { } // ❌ COMPILE ERROR

class Parent {
    final void cannotOverride() { }  // no child can override this
}
```

---

## 5. Polymorphism

**Definition:** "Many forms" — the same method name behaves differently depending on context.

### Compile-time Polymorphism (Method Overloading)
Same method name, different parameters — resolved at compile time.

```java
public class Calculator {
    // Same method name, different parameter types/counts
    public int add(int a, int b) {
        return a + b;
    }

    public double add(double a, double b) {
        return a + b;
    }

    public int add(int a, int b, int c) {
        return a + b + c;
    }

    public String add(String a, String b) {
        return a + b;  // string concatenation
    }
}

Calculator calc = new Calculator();
System.out.println(calc.add(2, 3));          // 5
System.out.println(calc.add(2.5, 3.5));      // 6.0
System.out.println(calc.add(1, 2, 3));       // 6
System.out.println(calc.add("Hello ", "World")); // Hello World
```

### Runtime Polymorphism (Method Overriding)
Child class provides its own version of parent method — resolved at runtime.

```java
Animal animal1 = new Dog("Rex", 3, "Labrador");
Animal animal2 = new Cat("Whiskers", 2, true);

// Both are Animal references, but call Dog/Cat versions
System.out.println(animal1.describe()); // Animal: Rex, Age: 3, Breed: Labrador
System.out.println(animal2.describe()); // Animal: Whiskers, Age: 2, Indoor: true

// Polymorphic array
Animal[] animals = { new Dog("Rex", 3, "Lab"), new Cat("Tom", 1, false) };
for (Animal a : animals) {
    a.eat();            // calls the correct version
    System.out.println(a.describe());
}
```

### instanceof operator
```java
if (animal1 instanceof Dog dog) {
    dog.fetch();  // safe to call Dog-specific method
}
```

---

## 6. Abstraction

**Definition:** Hiding implementation details and showing only what's necessary.

### Abstract Classes
```java
// Cannot be instantiated directly
public abstract class Shape {
    String color;

    public Shape(String color) {
        this.color = color;
    }

    // Abstract method — MUST be implemented by subclasses
    public abstract double calculateArea();
    public abstract double calculatePerimeter();

    // Concrete method — shared by all shapes
    public void displayInfo() {
        System.out.println("Shape: " + getClass().getSimpleName()
            + " | Color: " + color
            + " | Area: " + String.format("%.2f", calculateArea()));
    }
}

public class Circle extends Shape {
    double radius;

    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }
}

public class Rectangle extends Shape {
    double width, height;

    public Rectangle(String color, double width, double height) {
        super(color);
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() { return width * height; }

    @Override
    public double calculatePerimeter() { return 2 * (width + height); }
}
```

### Interfaces
```java
// Interface — a contract that classes must follow
public interface Drawable {
    void draw();           // implicitly public abstract
    void resize(int factor);

    // Default method (Java 8+) — has implementation
    default void printDescription() {
        System.out.println("I am a drawable object.");
    }
}

public interface Saveable {
    void save();
}

// A class can implement MULTIPLE interfaces
public class Canvas implements Drawable, Saveable {
    @Override
    public void draw() { System.out.println("Drawing on canvas..."); }

    @Override
    public void resize(int factor) { System.out.println("Resizing by " + factor); }

    @Override
    public void save() { System.out.println("Saving canvas..."); }
}
```

### Abstract Class vs Interface
| Feature | Abstract Class | Interface |
|---------|---------------|----------|
| Instantiation | ❌ Cannot | ❌ Cannot |
| Constructor | ✅ Can have | ❌ Cannot |
| Fields | Any type | Only `public static final` |
| Methods | Abstract + Concrete | Abstract + Default + Static |
| Inheritance | Single (`extends`) | Multiple (`implements`) |
| When to use | Shared base with state | Define a contract/capability |

---

## 7. Access Modifiers

| Modifier | Same Class | Same Package | Subclass | Everywhere |
|----------|-----------|--------------|----------|------------|
| `private` | ✅ | ❌ | ❌ | ❌ |
| `default` (no keyword) | ✅ | ✅ | ❌ | ❌ |
| `protected` | ✅ | ✅ | ✅ | ❌ |
| `public` | ✅ | ✅ | ✅ | ✅ |

```java
public class Example {
    private int a = 1;     // only within this class
    int b = 2;             // default: only within same package
    protected int c = 3;   // package + subclasses
    public int d = 4;      // accessible everywhere
}
```

---

## 8. Static Members

```java
public class Counter {
    // static variable — shared across ALL objects
    private static int count = 0;
    private String name;

    public Counter(String name) {
        this.name = name;
        count++;  // increments shared counter
    }

    // static method — belongs to class, not instance
    public static int getCount() {
        return count;
    }

    // static block — runs once when class is loaded
    static {
        System.out.println("Counter class loaded!");
    }
}

Counter c1 = new Counter("First");
Counter c2 = new Counter("Second");
Counter c3 = new Counter("Third");
System.out.println(Counter.getCount()); // 3 — call on class, not object
```

---

## 9. Object Class Methods

All Java classes implicitly extend `Object`. Override these methods:

```java
public class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // toString — called when printing an object
    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }

    // equals — compare objects by value
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return age == person.age && name.equals(person.name);
    }

    // hashCode — must be consistent with equals
    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, age);
    }
}

Person p1 = new Person("Punit", 25);
Person p2 = new Person("Punit", 25);
System.out.println(p1);            // Person{name='Punit', age=25}
System.out.println(p1.equals(p2)); // true (compares values)
System.out.println(p1 == p2);      // false (different objects)
```

---

## ✅ Best Practices
- Always use `@Override` annotation when overriding methods
- Prefer composition over inheritance when possible
- Keep classes small and focused (Single Responsibility Principle)
- Always override `toString()`, `equals()`, and `hashCode()` together
- Favour interfaces over abstract classes for defining contracts

## ⚠️ Common Mistakes
- Forgetting to call `super(...)` in child constructor
- Using `==` to compare objects instead of `.equals()`
- Making everything `public` — use the least permissive access modifier
- Confusing method overloading (compile-time) with overriding (runtime)

## 💡 Key Takeaways
- OOP models real-world entities as classes and objects
- Encapsulation protects data integrity
- Inheritance promotes code reuse
- Polymorphism makes code flexible and extensible
- Abstraction hides complexity

---

➡️ **Next:** [Module 03 — Java Advanced](../03-java-advanced/README.md)
