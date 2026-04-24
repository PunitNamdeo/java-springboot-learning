/**
 * EncapsulationExample.java
 * Demonstrates encapsulation with a BankAccount class
 */
public class EncapsulationExample {

    public static void main(String[] args) {
        BankAccount account = new BankAccount("Punit Namdeo", 5000.0);

        System.out.println("Owner: " + account.getOwner());
        System.out.println("Balance: " + account.getBalance());

        account.deposit(1500);
        account.withdraw(200);
        account.withdraw(10000); // insufficient

        System.out.println("Final Balance: " + account.getBalance());
    }
}

class BankAccount {
    private String owner;
    private double balance;

    public BankAccount(String owner, double balance) {
        this.owner = owner;
        this.balance = balance;
    }

    public String getOwner() { return owner; }
    public double getBalance() { return balance; }

    public void deposit(double amount) {
        if (amount <= 0) { System.out.println("Invalid deposit amount!"); return; }
        balance += amount;
        System.out.println("Deposited " + amount + " → Balance: " + balance);
    }

    public void withdraw(double amount) {
        if (amount > balance) { System.out.println("Insufficient funds!"); return; }
        balance -= amount;
        System.out.println("Withdrawn " + amount + " → Balance: " + balance);
    }
}
