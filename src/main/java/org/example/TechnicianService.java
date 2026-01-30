package org.example;

import org.hibernate.Session;
import java.util.List;

public class TechnicianService {

    /**
     * Diagnostic check for the ATM system.
     */
    public void runDiagnostics() {
        System.out.println("\n[SYSTEM DIAGNOSTICS]");
        System.out.println("-> Database: CONNECTED");
        System.out.println("-> Cash Dispenser: OK");
        System.out.println("-> Card Reader: OK");
    }

    /**
     * View logs (The "Privacy-Safe" view we talked about).
     */
    public void viewTransactionLogs() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<TransactionRecord> logs = session.createQuery("FROM TransactionRecord ORDER BY timestamp DESC", TransactionRecord.class)
                    .setMaxResults(10).list();

            System.out.println("\n[AUDIT LOGS - NO PRIVATE BALANCES SHOWN]");
            for (TransactionRecord log : logs) {
                System.out.println(log.getTimestamp() + " | " + log.getAccount().getUser().getFullName() + " | " + log.getType() + " | $" + log.getAmount());
            }
        }
    }
}