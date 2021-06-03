package CAB302.Common.Helpers;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    public static Session getHibernateSession() {

        SessionFactory sf = new Configuration()
                .configure("hibernate.cfg.xml").buildSessionFactory();

        Session session = sf.openSession();

        return session;
    }
}
