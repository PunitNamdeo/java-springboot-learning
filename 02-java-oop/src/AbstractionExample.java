/**
 * AbstractionExample.java
 * Demonstrates abstract classes and interfaces
 */
public class AbstractionExample {

    public static void main(String[] args) {
        // Abstract class usage
        Vehicle car = new CarVehicle("Toyota", 4);
        Vehicle bike = new BikeVehicle("Yamaha");

        car.start();
        car.describe();

        bike.start();
        bike.describe();

        // Interface usage
        System.out.println("\n--- Interface ---");
        Printable doc = new Document("Report.pdf");
        doc.print();
        doc.preview();
    }
}

// Abstract class
abstract class Vehicle {
    protected String brand;

    public Vehicle(String brand) { this.brand = brand; }

    // Abstract — subclasses MUST implement
    public abstract void start();

    // Concrete — shared by all vehicles
    public void stop() { System.out.println(brand + " stopped."); }
    public void describe() { System.out.println("Vehicle brand: " + brand); }
}

class CarVehicle extends Vehicle {
    int doors;
    public CarVehicle(String brand, int doors) { super(brand); this.doors = doors; }
    @Override public void start() { System.out.println(brand + " car (" + doors + " doors) started."); }
}

class BikeVehicle extends Vehicle {
    public BikeVehicle(String brand) { super(brand); }
    @Override public void start() { System.out.println(brand + " bike started with kick."); }
}

// Interface
interface Printable {
    void print();
    default void preview() { System.out.println("Previewing document..."); }
}

class Document implements Printable {
    private String filename;
    public Document(String filename) { this.filename = filename; }
    @Override public void print() { System.out.println("Printing: " + filename); }
}
