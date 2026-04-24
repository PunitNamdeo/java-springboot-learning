/**
 * StringsExample.java
 * Demonstrates String class methods, immutability, and StringBuilder
 */
public class StringsExample {

    public static void main(String[] args) {

        String name = "Punit Namdeo";

        // ── BASIC STRING METHODS ─────────────────────────────────────
        System.out.println("=== Basic String Methods ===");
        System.out.println("Original:          " + name);
        System.out.println("Length:            " + name.length());          // 12
        System.out.println("Uppercase:         " + name.toUpperCase());     // PUNIT NAMDEO
        System.out.println("Lowercase:         " + name.toLowerCase());     // punit namdeo
        System.out.println("Char at index 0:   " + name.charAt(0));        // P
        System.out.println("Index of 'Namdeo': " + name.indexOf("Namdeo")); // 6
        System.out.println("Substring(6):      " + name.substring(6));      // Namdeo
        System.out.println("Substring(0,5):    " + name.substring(0, 5));   // Punit
        System.out.println("Contains 'Punit':  " + name.contains("Punit")); // true
        System.out.println("Starts with 'Punit':" + name.startsWith("Punit")); // true
        System.out.println("Ends with 'eo':    " + name.endsWith("eo"));    // true
        System.out.println("Replace:           " + name.replace("Punit", "Java")); // Java Namdeo
        System.out.println("Trim:              " + "  hello  ".trim());     // hello
        System.out.println("IsEmpty:           " + "".isEmpty());           // true
        System.out.println("IsBlank:           " + "   ".isBlank());        // true (Java 11+)

        // ── STRING COMPARISON ────────────────────────────────────────
        System.out.println("\n=== String Comparison ===");
        String s1 = new String("hello");
        String s2 = new String("hello");
        System.out.println("s1 == s2:              " + (s1 == s2));          // false! (different objects)
        System.out.println("s1.equals(s2):         " + s1.equals(s2));       // true ✅
        System.out.println("equalsIgnoreCase:       " + s1.equalsIgnoreCase("HELLO")); // true
        System.out.println("compareTo (0=equal):   " + s1.compareTo(s2));    // 0

        // ── SPLITTING AND JOINING ────────────────────────────────────
        System.out.println("\n=== Split and Join ===");
        String csv = "apple,banana,cherry,date";
        String[] fruits = csv.split(",");
        for (String fruit : fruits) {
            System.out.println("  Fruit: " + fruit);
        }
        // Join back
        String joined = String.join(" | ", fruits);
        System.out.println("Joined: " + joined);

        // ── STRING FORMAT ────────────────────────────────────────────
        System.out.println("\n=== String.format ===");
        String formatted = String.format("Name: %-10s Age: %3d GPA: %.2f", "Punit", 25, 3.876);
        System.out.println(formatted);  // Name: Punit      Age:  25 GPA: 3.88

        // ── STRING BUILDER ───────────────────────────────────────────
        System.out.println("\n=== StringBuilder ===");
        // Use StringBuilder when building strings in a loop — much faster!
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 5; i++) {
            sb.append("module").append(i);
            if (i < 5) sb.append(", ");
        }
        System.out.println(sb.toString()); // module1, module2, module3, module4, module5

        sb.insert(0, "Modules: ");  // insert at position 0
        sb.reverse();               // reverse the string
        System.out.println("Reversed: " + sb.toString());

        // ── IMMUTABILITY DEMO ────────────────────────────────────────
        System.out.println("\n=== String Immutability ===");
        String original = "Hello";
        String modified = original.concat(" World");  // creates NEW string
        System.out.println("Original: " + original);  // Hello — unchanged!
        System.out.println("Modified: " + modified);  // Hello World
    }
}
