package com.sad.sadcrm.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import javax.swing.*;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (Throwable exception) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + exception);
            throw new ExceptionInInitializerError(exception);
        }
    }

    static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void checkConnection() {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.close();
        } catch (Throwable ex) {
            JOptionPane.showMessageDialog(null,
                    "Błąd połączenia z bazą danych.\nSprawdź serwer bazy danych jest uruchomiony\n i poprawnie skonfigurowany.",
                    "Błąd bazy danych",
                    JOptionPane.ERROR_MESSAGE);

            System.exit(1);
        }
    }
}
