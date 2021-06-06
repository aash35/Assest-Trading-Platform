package CAB302.Common.Helpers;

import CAB302.Common.ServerPackages.RuntimeSettings;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.resource.transaction.spi.TransactionStatus;

/**
 * Contains methods for utilising Hibernate.
 */
public class HibernateUtil {

    /**
     * Method creates a new Hibernate session.
     * @return a Hibernate session.
     */
    public static Session getHibernateSession() {

        SessionFactory sf = new Configuration()
                .configure("hibernate.cfg.xml").buildSessionFactory();

        Session session = sf.openSession();

        return session;
    }

    /**
     * Method either retrieves a transaction if one exists in the Hibernate session or opens a transaction otherwise.
     */
    public static void openOrGetTransaction() {

        Session session = RuntimeSettings.Session;

        try {
            Transaction transaction = session.getTransaction();

            if (transaction.getStatus() == TransactionStatus.NOT_ACTIVE || transaction.getStatus() == TransactionStatus.COMMITTED) {
                session.beginTransaction();
            }
        }
        catch (Exception ex) {
            session.beginTransaction();
        }
    }
}
