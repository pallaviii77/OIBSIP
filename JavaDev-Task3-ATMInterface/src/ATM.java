import java.util.ArrayList;
import java.util.Scanner;

public class ATM {

    private Bank bank;
    private Account currentAccount;
    private ArrayList<Transaction> transactions;
    private Scanner scanner;

    public ATM(Bank bank) {
        this.bank = bank;
        this.transactions = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void start() {

        int choice;

        do {

            System.out.println("\n========== ATM ==========");
            System.out.println("1. Login");
            System.out.println("2. Create New Account");
            System.out.println("3. Exit");
            System.out.println("=========================");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:

                    if (login()) {

                        System.out.println("\nLogin successful!");
                        System.out.println("Welcome, " + currentAccount.getUserId());

                        showMenu();

                    } else {

                        System.out.println("Access denied.");
                    }

                    break;

                case 2:
                    createAccount();
                    break;

                case 3:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 3);
    }

    private boolean login() {

        int attempts = 0;

        while (attempts < 3) {

            System.out.print("Enter User ID: ");
            String userId = scanner.nextLine();

            System.out.print("Enter PIN: ");
            int pin = scanner.nextInt();
            scanner.nextLine();

            Account account = bank.findAccount(userId);

            if (account != null && account.getPin() == pin) {

                currentAccount = account;
                return true;
            }

            attempts++;

            System.out.println("Incorrect User ID or PIN.");
            System.out.println("Attempts remaining: " + (3 - attempts));
        }

        return false;
    }

    private void createAccount() {

        System.out.println("\n========== CREATE ACCOUNT ==========");

        System.out.print("Enter new User ID: ");
        String userId = scanner.nextLine();

        if (bank.findAccount(userId) != null) {

            System.out.println("User ID already exists.");
            return;
        }

        System.out.print("Create PIN: ");
        int pin = scanner.nextInt();

        System.out.print("Enter initial deposit: $");
        double balance = scanner.nextDouble();
        scanner.nextLine();

        if (balance < 0) {

            System.out.println("Invalid initial deposit.");
            return;
        }

        Account newAccount = new Account(userId, pin, balance);

        if (bank.addAccount(newAccount)) {

            System.out.println("\nAccount created successfully!");
            System.out.println("Your User ID: " + userId);
            System.out.println("Your Balance: ₹" + balance);

        } else {

            System.out.println("Account creation failed.");
        }
    }

    private void showMenu() {

        int choice;

        do {

            System.out.println("\n========== ATM MENU ==========");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.println("==============================");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    showTransactionHistory();
                    break;

                case 2:
                    withdraw();
                    break;

                case 3:
                    deposit();
                    break;

                case 4:
                    transfer();
                    break;

                case 5:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 5);
    }

    private void showTransactionHistory() {

        System.out.println("\n========== TRANSACTION HISTORY ==========");

        if (transactions.isEmpty()) {

            System.out.println("No transactions made in this session.");

        } else {

            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }

        System.out.println("=========================================");
        System.out.println("Current Balance: $" + currentAccount.getBalance());
    }

    private void withdraw() {

        System.out.print("Enter amount to withdraw: $");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        if (amount <= 0) {

            System.out.println("Invalid amount.");
            return;
        }

        if (currentAccount.getBalance() < amount) {

            System.out.println("Insufficient Funds");
            return;
        }

        currentAccount.setBalance(
            currentAccount.getBalance() - amount
        );

        transactions.add(
            new Transaction(
                "Withdraw",
                amount,
                "Cash withdrawn"
            )
        );

        System.out.println("Withdrawal successful.");
        System.out.println("Amount withdrawn: $" + amount);
        System.out.println("Remaining balance: $" + currentAccount.getBalance());
    }

    private void deposit() {

        System.out.print("Enter amount to deposit: $");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        if (amount <= 0) {

            System.out.println("Invalid amount.");
            return;
        }

        currentAccount.setBalance(
            currentAccount.getBalance() + amount
        );

        transactions.add(
            new Transaction(
                "Deposit",
                amount,
                "Cash deposited"
            )
        );

        System.out.println("Deposit successful.");
        System.out.println("Amount deposited: $" + amount);
        System.out.println("Current balance: $" + currentAccount.getBalance());
    }

    private void transfer() {

        System.out.print("Enter recipient account ID: ");
        String recipientId = scanner.nextLine();

        Account recipient = bank.findAccount(recipientId);

        if (recipient == null) {

            System.out.println("Recipient account not found.");
            return;
        }

        if (recipient == currentAccount) {

            System.out.println("You cannot transfer money to your own account.");
            return;
        }

        System.out.print("Enter amount to transfer: $");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        if (amount <= 0) {

            System.out.println("Invalid amount.");
            return;
        }

        if (currentAccount.getBalance() < amount) {

            System.out.println("Insufficient Funds");
            return;
        }

        currentAccount.setBalance(
            currentAccount.getBalance() - amount
        );

        recipient.setBalance(
            recipient.getBalance() + amount
        );

        transactions.add(
            new Transaction(
                "Transfer",
                amount,
                "Transferred to account " + recipientId
            )
        );

        System.out.println("Transfer successful.");
        System.out.println("Amount transferred: $" + amount);
        System.out.println("Remaining balance: $" + currentAccount.getBalance());
    }
}