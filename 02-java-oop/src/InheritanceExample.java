/**
 * InheritanceExample.java
 * Demonstrates inheritance with Animal → Dog / Cat hierarchy
 */
public class InheritanceExample {

    public static void main(String[] args) {
        Dog dog = new Dog("Rex", 3, "Labrador");
        Cat cat = new Cat("Whiskers", 2, true);

        dog.eat();
        dog.fetch();
        System.out.println(dog.describe());

        cat.eat();
        cat.purr();
        System.out.println(cat.describe());

        // Polymorphic reference
        Animal[] animals = {dog, cat};
        System.out.println("\n--- All Animals ---");
        for (Animal a : animals) {
            System.out.println(a.describe());
        }
    }
}

class Animal {
    protected String name;
    protected int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void eat() { System.out.println(name + " is eating."); }
    public void sleep() { System.out.println(name + " is sleeping."); }
    public String describe() { return "Animal: " + name + ", Age: " + age; }
}

class Dog extends Animal {
    private String breed;

    public Dog(String name, int age, String breed) {
        super(name, age);
        this.breed = breed;
    }

    public void fetch() { System.out.println(name + " fetches the ball! 🎾"); }

    @Override
    public String describe() { return super.describe() + ", Breed: " + breed; }
}

class Cat extends Animal {
    private boolean isIndoor;

    public Cat(String name, int age, boolean isIndoor) {
        super(name, age);
        this.isIndoor = isIndoor;
    }

    public void purr() { System.out.println(name + " purrs... 😺"); }

    @Override
    public String describe() { return super.describe() + ", Indoor: " + isIndoor; }
}
