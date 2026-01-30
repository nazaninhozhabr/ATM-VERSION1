package org.example;

import jakarta.persistence.*;

/**
 * User Entity following the Single Responsibility Principle.
 * Encapsulates user data and authentication details.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "card_number") // Used as Login ID for Customers
    private String cardNumber;

    @Column(name = "username")    // Used as Login ID for Technicians
    private String username;

    private String pin;
    private String role; // Role can be 'CUSTOMER' or 'TECHNICIAN'

    // Getters
    public int getId() { return id; }
    public String getFullName() { return fullName; }
    public String getCardNumber() { return cardNumber; }
    public String getUsername() { return username; }
    public String getPin() { return pin; }
    public String getRole() { return role; }
}