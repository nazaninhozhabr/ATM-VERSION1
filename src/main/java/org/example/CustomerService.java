package org.example;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDateTime;

public class CustomerService {

    public void checkBalance(int userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Account acc = session.createQuery("FROM Account WHERE user.id = :uid", Account.class)
                    .setParameter("uid", userId).uniqueResult();
            if (acc != null) {
                System.out.println("\n>>> CURRENT BALANCE: $" + acc.getBalance());
            }
        }
    }

    public void deposit(int userId, double amount) {
        process(userId, amount, "DEPOSIT");
    }

    public void withdraw(int userId, double amount) {
        process(userId, amount, "WITHDRAW");
    }

    public void transfer(int fromUserId, String targetCard, double amount) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Account source = session.createQuery("FROM Account WHERE user.id = :uid", Account.class)
                    .setParameter("uid", fromUserId).uniqueResult();
            Account dest = session.createQuery("FROM Account WHERE user.cardNumber = :c", Account.class)
                    .setParameter("c", targetCard).uniqueResult();

            if (dest != null && source != null && source.getBalance() >= amount) {
                source.setBalance(source.getBalance() - amount);
                dest.setBalance(dest.getBalance() + amount);
                session.merge(source);
                session.merge(dest);

                logAction(session, source, "TRANSFER OUT", amount);
                logAction(session, dest, "TRANSFER IN", amount);

                tx.commit();
                System.out.println("✅ Transfer Successful!");
            } else {
                System.out.println("❌ Transfer Failed: Check balance or target card.");
            }
        } catch (Exception e) { if (tx != null) tx.rollback(); }
    }

    private void process(int userId, double amount, String type) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Account acc = session.createQuery("FROM Account WHERE user.id = :uid", Account.class)
                    .setParameter("uid", userId).uniqueResult();

            if (acc != null) {
                if (type.equals("WITHDRAW") && acc.getBalance() < amount) {
                    System.out.println("❌ Insufficient funds!");
                    return;
                }
                acc.setBalance(type.equals("DEPOSIT") ? acc.getBalance() + amount : acc.getBalance() - amount);
                session.merge(acc);
                logAction(session, acc, type, amount);
                tx.commit();
                System.out.println("✅ " + type + " successful!");
            }
        } catch (Exception e) { if (tx != null) tx.rollback(); }
    }

    private void logAction(Session session, Account acc, String type, double amount) {
        TransactionRecord log = new TransactionRecord();
        log.setAccount(acc);
        log.setType(type);
        log.setAmount(amount);
        log.setTimestamp(LocalDateTime.now());
        session.persist(log);
    }
}