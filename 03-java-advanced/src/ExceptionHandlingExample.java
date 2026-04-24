/**
 * ExceptionHandlingExample.java
 * Demonstrates try-catch-finally, custom exceptions, multi-catch
 */
public class ExceptionHandlingExample {

    public static void main(String[] args) {

        // --- Basic try-catch-finally ---
        System.out.println("=== Basic Exception Handling ===");
        try {
            int result = divide(10, 0);
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            System.out.println("Caught ArithmeticException: " + e.getMessage());
        } finally {
            System.out.println("Finally block always runs.");
        }

        // --- Custom Exception ---
        System.out.println("\n=== Custom Exception ===");
        try {
            withdraw(500, 200);
            withdraw(200, 500);  // this throws
        } catch (InsufficientFundsException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        // --- Multi-catch ---
        System.out.println("\n=== Multi-catch ===");
        try {
            String s = null;
            s.length();  // NullPointerException
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println("Caught multi: " + e.getClass().getSimpleName());
        }
    }

    static int divide(int a, int b) {
        return a / b;
    }

    static void withdraw(double balance, double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException(
                "Cannot withdraw " + amount + ". Balance is only " + balance);
        }
        System.out.println("Withdrew " + amount + ". Remaining: " + (balance - amount));
    }
}

// Custom unchecked exception
class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
