/**
 * PolymorphismExample.java
 * Demonstrates method overloading (compile-time) and overriding (runtime)
 */
public class PolymorphismExample {

    public static void main(String[] args) {
        // --- Method Overloading ---
        System.out.println("=== Method Overloading (Compile-time) ===");
        Calculator calc = new Calculator();
        System.out.println(calc.add(2, 3));
        System.out.println(calc.add(2.5, 3.5));
        System.out.println(calc.add(1, 2, 3));
        System.out.println(calc.add("Hello ", "World"));

        // --- Method Overriding ---
        System.out.println("\n=== Method Overriding (Runtime) ===");
        Shape[] shapes = {
            new CircleShape(5),
            new RectangleShape(4, 6),
            new TriangleShape(3, 4, 5)
        };
        for (Shape s : shapes) {
            s.draw();  // calls the correct overridden version
        }
    }
}

class Calculator {
    public int add(int a, int b) { return a + b; }
    public double add(double a, double b) { return a + b; }
    public int add(int a, int b, int c) { return a + b + c; }
    public String add(String a, String b) { return a + b; }
}

abstract class Shape {
    abstract void draw();
}

class CircleShape extends Shape {
    int radius;
    CircleShape(int r) { this.radius = r; }
    @Override void draw() { System.out.println("Drawing Circle with radius " + radius); }
}

class RectangleShape extends Shape {
    int w, h;
    RectangleShape(int w, int h) { this.w = w; this.h = h; }
    @Override void draw() { System.out.println("Drawing Rectangle " + w + "x" + h); }
}

class TriangleShape extends Shape {
    int a, b, c;
    TriangleShape(int a, int b, int c) { this.a = a; this.b = b; this.c = c; }
    @Override void draw() { System.out.println("Drawing Triangle with sides " + a + "," + b + "," + c); }
}
