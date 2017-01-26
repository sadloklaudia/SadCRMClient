/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sad.sadcrm.hibernate;

import com.sad.sadcrm.model.User;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author dstachyra
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
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
