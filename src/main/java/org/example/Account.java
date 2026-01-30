package org.example;

import jakarta.persistence.*;

/**
 * Account entity representing the bank account of a user.
 * Following SOLID: This class has a Single Responsibility of holding account data.
 */
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Establishing a One-to-One relationship with the User entity
    // This allows us to access user details directly from the account object
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "balance")
    private double balance;

    // Default constructor required by Hibernate
    public Account() {}

    // GETTERS AND SETTERS
    // Providing public access to private fields encapsulated within the class (Encapsulation)

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}