/**
 * ControlFlowExample.java
 * Demonstrates if/else, switch, for, while, do-while, break, continue
 */
public class ControlFlowExample {

    public static void main(String[] args) {

        // ── IF / ELSE IF / ELSE ──────────────────────────────────────
        System.out.println("=== If/Else ===");
        int score = 82;

        if (score >= 90) {
            System.out.println("Grade: A");
        } else if (score >= 80) {
            System.out.println("Grade: B");  // ← prints this
        } else if (score >= 70) {
            System.out.println("Grade: C");
        } else {
            System.out.println("Grade: F");
        }

        // ── TERNARY OPERATOR ─────────────────────────────────────────
        String status = (score >= 60) ? "Pass" : "Fail";
        System.out.println("Status: " + status);

        // ── SWITCH ───────────────────────────────────────────────────
        System.out.println("\n=== Switch ===");
        String day = "WEDNESDAY";

        switch (day) {
            case "MONDAY":
            case "TUESDAY":
            case "WEDNESDAY":
            case "THURSDAY":
            case "FRIDAY":
                System.out.println(day + " is a weekday.");
                break;
            case "SATURDAY":
            case "SUNDAY":
                System.out.println(day + " is a weekend.");
                break;
            default:
                System.out.println("Invalid day.");
        }

        // Modern switch expression (Java 14+)
        String type = switch (day) {
            case "SATURDAY", "SUNDAY" -> "Weekend";
            default -> "Weekday";
        };
        System.out.println(day + " → " + type);

        // ── FOR LOOP ─────────────────────────────────────────────────
        System.out.println("\n=== For Loop ===");
        for (int i = 1; i <= 5; i++) {
            System.out.print(i + " ");  // 1 2 3 4 5
        }
        System.out.println();

        // ── WHILE LOOP ───────────────────────────────────────────────
        System.out.println("\n=== While Loop ===");
        int countdown = 5;
        while (countdown > 0) {
            System.out.print(countdown + " ");
            countdown--;
        }
        System.out.println("Go!");

        // ── DO-WHILE LOOP ────────────────────────────────────────────
        System.out.println("\n=== Do-While Loop ===");
        int num = 10;
        do {
            System.out.println("Executed once even though " + num + " > 5");
            num++;
        } while (num < 5);  // condition is false, but body runs once

        // ── BREAK AND CONTINUE ───────────────────────────────────────
        System.out.println("\n=== Break and Continue ===");
        for (int i = 0; i < 10; i++) {
            if (i == 3) {
                System.out.print("[skip3] ");  // skip 3
                continue;
            }
            if (i == 7) {
                System.out.print("[stop7]");   // stop loop
                break;
            }
            System.out.print(i + " ");
        }
        // Output: 0 1 2 [skip3] 4 5 6 [stop7]
        System.out.println();

        // ── NESTED LOOPS ─────────────────────────────────────────────
        System.out.println("\n=== Multiplication Table (3x3) ===");
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                System.out.printf("%4d", i * j);
            }
            System.out.println();
        }
    }
}
