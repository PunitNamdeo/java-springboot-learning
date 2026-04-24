/**
 * DependencyInjectionExample.java
 * Demonstrates Constructor, Setter, and Field injection concepts
 * (Plain Java simulation — in real Spring Boot, annotations handle this)
 */
public class DependencyInjectionExample {

    public static void main(String[] args) {

        // --- Constructor Injection (RECOMMENDED) ---
        System.out.println("=== Constructor Injection ===");
        EmailService emailService = new EmailService();
        SmsService smsService = new SmsService();

        // Spring would do this automatically:
        UserService userService = new UserService(emailService, smsService);
        userService.registerUser("Punit", "punit@example.com");

        // --- Benefits of Constructor Injection ---
        System.out.println("\n=== Benefits ===");
        // 1. Dependencies are final (immutable)
        // 2. Easy to test: just pass mock objects
        // 3. Explicit: you can see all dependencies in constructor
        System.out.println("All dependencies clearly visible in constructor.");
        System.out.println("Can use final fields for immutability.");
        System.out.println("Easy to unit test by passing mock objects.");
    }
}

// Service interfaces
interface NotificationService {
    void notify(String recipient, String message);
}

// Concrete implementations
class EmailService implements NotificationService {
    @Override
    public void notify(String recipient, String message) {
        System.out.println("[EMAIL] To: " + recipient + " | Message: " + message);
    }
}

class SmsService implements NotificationService {
    @Override
    public void notify(String recipient, String message) {
        System.out.println("[SMS] To: " + recipient + " | Message: " + message);
    }
}

// UserService depends on NotificationService — injected via constructor
class UserService {
    private final NotificationService emailService;  // final = immutable
    private final NotificationService smsService;

    // Constructor Injection
    public UserService(NotificationService emailService, NotificationService smsService) {
        this.emailService = emailService;
        this.smsService = smsService;
    }

    public void registerUser(String name, String email) {
        System.out.println("Registering user: " + name);
        emailService.notify(email, "Welcome to our platform, " + name + "!");
        smsService.notify("+91-9999999999", "Hi " + name + ", your account is ready.");
    }
}
