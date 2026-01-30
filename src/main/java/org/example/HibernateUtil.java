package org.example;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Singleton Utility class for Hibernate SessionFactory.
 * Following SOLID principles: Single Responsibility for managing DB connections.
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory;

    // Private constructor to prevent instantiation (Singleton Rule)
    private HibernateUtil() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Building the factory from hibernate.cfg.xml
                sessionFactory = new Configuration().configure().buildSessionFactory();
            } catch (Exception e) {
                System.err.println("Initial SessionFactory creation failed." + e);
                throw new ExceptionInInitializerError(e);
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }
}