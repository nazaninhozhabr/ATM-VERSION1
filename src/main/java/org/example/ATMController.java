package org.example;

import org.hibernate.Session;
import java.util.Scanner;

public class ATMController {
    private static ATMController instance;
    private final Scanner scanner = new Scanner(System.in);
    private final CustomerService customerService = new CustomerService();
    private final TechnicianService technicianService = new TechnicianService();
    private User loggedInUser;

    private ATMController() {}

    public static ATMController getInstance() {
        if (instance == null) instance = new ATMController();
        return instance;
    }

    public void start() {
        while (true) {
            System.out.println("\n====================================");
            System.out.println("      WELCOME TO GLOBAL BANK ATM    ");
            System.out.println("====================================");
            System.out.println(" [1] CUSTOMER LOGIN\n [2] TECHNICIAN LOGIN\n [3] EXIT");
            System.out.print("\nSelection > ");
            String choice = scanner.nextLine();
            if (choice.equals("3")) break;
            handleLogin(choice.equals("1") ? "CUSTOMER" : "TECHNICIAN");
        }
    }

    private void handleLogin(String role) {
        System.out.print("Enter ID: "); String id = scanner.nextLine();
        System.out.print("Enter PIN: "); String pin = scanner.nextLine();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            loggedInUser = session.createQuery("FROM User WHERE (cardNumber=:id OR username=:id) AND pin=:p AND role=:r", User.class)
                    .setParameter("id", id).setParameter("p", pin).setParameter("r", role)
                    .setMaxResults(1).uniqueResult();

            if (loggedInUser != null) {
                System.out.println("\n✅ Login Success. Welcome " + loggedInUser.getFullName());
                if (role.equals("CUSTOMER")) showCustomerMenu();
                else showTechnicianMenu();
            } else System.out.println("❌ Invalid credentials.");
        }
    }

    private void showCustomerMenu() {
        while (true) {
            System.out.println("\n--- CUSTOMER DASHBOARD ---");
            System.out.println("1. Balance | 2. Deposit | 3. Withdraw | 4. Transfer | 5. Logout");
            System.out.print("Action > ");
            String c = scanner.nextLine();
            if (c.equals("5")) break;

            switch (c) {
                case "1" -> customerService.checkBalance(loggedInUser.getId());
                case "2" -> {
                    System.out.print("Amount to Deposit: ");
                    customerService.deposit(loggedInUser.getId(), Double.parseDouble(scanner.nextLine()));
                }
                case "3" -> {
                    System.out.print("Amount to Withdraw: ");
                    customerService.withdraw(loggedInUser.getId(), Double.parseDouble(scanner.nextLine()));
                }
                case "4" -> {
                    System.out.print("Enter Target Card Number: ");
                    String target = scanner.nextLine();
                    System.out.print("Amount to Transfer: ");
                    customerService.transfer(loggedInUser.getId(), target, Double.parseDouble(scanner.nextLine()));
                }
            }
        }
    }

    private void showTechnicianMenu() {
        while (true) {
            System.out.println("\n--- TECHNICIAN DASHBOARD (SECURE) ---");
            System.out.println("1. System Diagnostics | 2. View Audit Logs | 3. Logout");
            System.out.print("Action > ");
            String c = scanner.nextLine();
            if (c.equals("3")) break;

            switch (c) {
                case "1" -> technicianService.runDiagnostics();
                case "2" -> technicianService.viewTransactionLogs();
            }
        }
    }
}