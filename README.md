# ATM VERSION 1

Software Engineering Final Project  
**OHM – Level 5 – Information Technology**

---

## 📌 Project Description

ATM VERSION 1 is a Java-based ATM Machine system developed using **Object-Oriented Programming (OOP)** concepts and **SOLID design principles**.  
The system simulates basic ATM functionalities and demonstrates clean software architecture and separation of concerns.

Hibernate is used for data persistence, and the project is managed using Maven.

---

## 🎯 Objectives

- Apply Object-Oriented Programming principles
- Implement SOLID design principles
- Demonstrate clean code and layered architecture
- Practice version control using Git and GitHub

---

## ⚙️ Features

- User authentication
- Balance inquiry
- Cash deposit
- Cash withdrawal
- Transaction records
- Technician/maintenance operations

---

## 🏗️ Project Structure

- **Controller**
  - `ATMController` – Manages application flow and user interaction
- **Models / Entities**
  - `User`
  - `Account`
  - `TransactionRecord`
- **Services**
  - `CustomerService` – Handles customer operations
  - `TechnicianService` – Handles technician-related operations
- **Persistence**
  - Hibernate configuration (`hibernate.cfg.xml`)
  - `HibernateUtil`
- **Entry Point**
  - `App.java`

---

## 🧠 SOLID Principles Applied

- **Single Responsibility Principle (SRP)**  
  Each class has a single responsibility (e.g., services handle business logic, models represent data).

- **Open/Closed Principle (OCP)**  
  The system is designed to allow extension without modifying existing code.

- **Liskov Substitution Principle (LSP)**  
  Components can be replaced or extended without breaking functionality.

- **Interface Segregation Principle (ISP)**  
  Responsibilities are separated into focused classes and services.

- **Dependency Inversion Principle (DIP)**  
  High-level modules depend on abstractions rather than concrete implementations.

---

## 🛠️ Technologies Used

- Java
- Maven
- Hibernate
- IntelliJ IDEA
- Git & GitHub

---

## ▶️ How to Run the Project

1. Clone the repository:
   ```bash
   git clone https://github.com/nazaninhozhabr/ATM-VERSION1.git
